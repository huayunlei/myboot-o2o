package com.ihomefnt.o2o.service.service.common;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.common.concurrent.TaskAction;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.domain.dingtalk.dto.AiDingTalkRecordDto;
import com.ihomefnt.o2o.intf.domain.user.dto.AiMonitorDto;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.util.common.http.HttpClientUtil;
import com.ihomefnt.o2o.intf.proxy.common.AiDingTalkRecordProxy;
import com.ihomefnt.o2o.intf.proxy.common.AiMonitorConfigProxy;
import com.ihomefnt.o2o.intf.service.common.AiDingTalk;
import com.ihomefnt.semporna.context.IhomeContextHandler;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AiDingTalkImpl implements AiDingTalk {
    @Autowired
    private AiDingTalkRecordProxy aiDingTalkRecordProxy;

    /**
     * 增加日志:主要为了方便定位
     */
    private static final Logger LOG = LoggerFactory.getLogger(AiDingTalk.class);

    /**
     * DING_URL
     */
    private static final String DING_URL = "https://oapi.dingtalk.com/robot/send?access_token=";

    /**
     * ## traceId跳转链接
     */
    @NacosValue(value = "${sky.trace.id.url}", autoRefreshed = true)
    private String skyTraceIdUrl;

    /**
     * ## orderId跳转链接
     */
    @NacosValue(value = "${warn.order.id.url}", autoRefreshed = true)
    private String warnOrderIdUrl;


    @Autowired
    AiMonitorConfigProxy aiMonitorConfigProxy;

    /**
     * 组装并发送钉钉告警
     *
     * @param classMethodName 类名.方法名
     * @param url             接口名
     * @param params          入参
     * @param code            错误码
     * @param msg             错误信息
     */
    @Override
    public void sendDingTalkWarn(String classMethodName, String url, String params, Long code, String msg) {
        List<AiMonitorDto> aiMonitorDtoList = aiMonitorConfigProxy.getMonitorByKey(classMethodName);

        if (aiMonitorDtoList.size() < 1) {
            return;
        }
        for (AiMonitorDto aiMonitorDto : aiMonitorDtoList) {
            JSONObject sendParams = new JSONObject();
            sendParams.put("msgtype", "markdown");
            if (StringUtil.isNotBlank(aiMonitorDto.getMonitorAtMobile())) {
                JSONObject map2 = new JSONObject();
                map2.put("isAtAll", false);
                map2.put("atMobiles", aiMonitorDto.getMonitorAtMobile().split(","));
                sendParams.put("at", map2);
            }
            String currentTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
            JSONObject textObject = new JSONObject();
            String urlMsg = url == null ? "#无#" : url;
            String paramsMsg = "";
            String mobileNum = "";
            String appVersion = "";
            if (params != null) {
                JSONObject jsonObject = JSONObject.parseObject(params);
                mobileNum = jsonObject.getString("mobileNum");
                String orderNum = jsonObject.getString("orderNum");
                String draftId = jsonObject.getString("draftId");
                appVersion = jsonObject.getString("appVersion");
                if (orderNum == null) {
                    orderNum = jsonObject.getString("orderId");
                }
                paramsMsg += mobileNum == null ? "" : "\n > - 手机号(" + mobileNum + ")";

                String orderString = null;
                if (orderNum != null) {
                    orderString = "[" + orderNum + "](" + warnOrderIdUrl.replace("orderIdReplace", orderNum) + ")";
                }
                paramsMsg += orderNum == null ? "" : "\n > - 订单号(" + orderString + ")";
                paramsMsg += draftId == null ? "" : "\n > - 草稿ID(" + draftId + ")";
                if ("".equals(paramsMsg)) {
                    paramsMsg = "\n > - 请求参数：" + params + "\r\n";
                }
            } else {
                paramsMsg = "\n > - 请求参数为空\r\n";
            }
            String codeMsg = code == null ? "#无#" : code.toString();
            String errorMsg = msg == null ? "#无#" : msg;

            String traceIdUrl = skyTraceIdUrl.replace("replaceTraceId", IhomeContextHandler.getIhomeContext().getTraceId());
            String content = "## " + aiMonitorDto.getMonitorDesc() +
                    paramsMsg +
                    "\n > - APP版本：" + (StringUtil.isEmpty(appVersion) ? "未知" : appVersion) +
                    "\n > - 风险等级：**" + aiMonitorDto.getMonitorLevel() + "**" +
                    "\n > - 接口URL：" + urlMsg +
                    "\n > - 响应信息：code：" + codeMsg +
                    "\n > - 错误信息：" + errorMsg +
                    "\n > - " + traceIdUrl +
                    "\n > - 报错时间：" + currentTime;

            textObject.put("title", "o2o接口告警");
            textObject.put("text", content);
            sendParams.put("markdown", textObject);
            //发送钉钉
            sendDingTalk(aiMonitorDto.getMonitorDingToken(), sendParams.toString());

            AiDingTalkRecordDto aiDingTalkRecordDto = new AiDingTalkRecordDto();
            aiDingTalkRecordDto.setRecordKey(aiMonitorDto.getMonitorKey())
                    .setRecordMobile(mobileNum != null ? mobileNum : "")
                    .setRecordDesc(aiMonitorDto.getMonitorDesc())
                    .setRecordDingToken(aiMonitorDto.getMonitorDingToken())
                    .setRecordDingMsg(content);
            aiDingTalkRecordProxy.addDingTalkRecord(aiDingTalkRecordDto);
        }
    }

    /**
     * 组装并发送钉钉消息
     *
     * @param classMethodName 方法名
     * @param params          请求参数
     * @param draftPrice      草稿价格
     * @param contractAmount  下单价格
     */
    public void sendDingTalkInfo(String classMethodName, String params, BigDecimal draftPrice, BigDecimal contractAmount) {
        List<AiMonitorDto> aiMonitorDtoList = aiMonitorConfigProxy.getMonitorByKey(classMethodName);

        if (CollectionUtils.isEmpty(aiMonitorDtoList)) {
            return;
        }
        for (AiMonitorDto aiMonitorDto : aiMonitorDtoList) {
            JSONObject sendParams = new JSONObject();
            sendParams.put("msgtype", "markdown");
            if (StringUtil.isNotBlank(aiMonitorDto.getMonitorAtMobile())) {
                JSONObject map2 = new JSONObject();
                map2.put("isAtAll", false);
                map2.put("atMobiles", aiMonitorDto.getMonitorAtMobile().split(","));
                sendParams.put("at", map2);
            }
            String currentTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
            JSONObject textObject = new JSONObject();
            String paramsMsg = params == null ? "#无#" : params;

            String traceIdUrl = skyTraceIdUrl.replace("replaceTraceId", IhomeContextHandler.getIhomeContext().getTraceId());
            String content = "## " + aiMonitorDto.getMonitorDesc() +
                    "\n > - 风险等级：**" + aiMonitorDto.getMonitorLevel() + "**" +
                    "\n > - 草稿价格（" + draftPrice + "），下单价格（" + contractAmount + "），差价（" + draftPrice.subtract(contractAmount) + "）" +
                    "\n > - " + paramsMsg +
                    "\n > - " + traceIdUrl +
                    "\n > - 报错时间：" + currentTime;

            textObject.put("title", "价格校验告警");
            textObject.put("text", content);
            sendParams.put("markdown", textObject);

            //发送钉钉
            sendDingTalk(aiMonitorDto.getMonitorDingToken(), sendParams.toString());
            AiDingTalkRecordDto aiDingTalkRecordDto = new AiDingTalkRecordDto();
            aiDingTalkRecordDto.setRecordKey(aiMonitorDto.getMonitorKey())
                    .setRecordMobile("")
                    .setRecordDesc(aiMonitorDto.getMonitorDesc())
                    .setRecordDingToken(aiMonitorDto.getMonitorDingToken())
                    .setRecordDingMsg(content);
            aiDingTalkRecordProxy.addDingTalkRecord(aiDingTalkRecordDto);
        }
    }


    /**
     * 组装并发送钉钉告警
     *
     * @param classMethodName 类名.方法名
     * @param url             接口名
     * @param params          入参
     * @param code            错误码
     * @param msg             错误信息
     */
    @Override
    public void sendOrderDingTalkWarn(String classMethodName, String url, String params, Long code, String msg) {
        List<AiMonitorDto> aiMonitorDtoList = aiMonitorConfigProxy.getMonitorByKey(classMethodName);

        if (aiMonitorDtoList.size() < 1) {
            return;
        }
        for (AiMonitorDto aiMonitorDto : aiMonitorDtoList) {
            JSONObject sendParams = new JSONObject();
            sendParams.put("msgtype", "text");
            if (StringUtil.isNotBlank(aiMonitorDto.getMonitorAtMobile())) {
                JSONObject map2 = new JSONObject();
                map2.put("isAtAll", false);
                map2.put("atMobiles", aiMonitorDto.getMonitorAtMobile().split(","));
                sendParams.put("at", map2);
            }
            String currentTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
            JSONObject textObject = new JSONObject();
            String urlMsg = url == null ? "#无#" : url;
            String paramsMsg = "";
            if (params != null) {
                paramsMsg = "请求参数：" + params;
            } else {
                paramsMsg = "请求参数为空";
            }
            String codeMsg = code == null ? "#无#" : code.toString();
            String errorMsg = msg == null ? "#无#" : msg;

            String traceIdUrl = skyTraceIdUrl.replace("replaceTraceId", IhomeContextHandler.getIhomeContext().getTraceId());
            String content = "风险等级：" + aiMonitorDto.getMonitorLevel() + "\r\n" + aiMonitorDto.getMonitorDesc() +
                    "\r\n接口URL：" + urlMsg + "\r\n" + paramsMsg +
                    "\r\n响应信息：code：" + codeMsg + "\r\n错误信息：" + errorMsg +
                    "\r\n" + traceIdUrl +
                    "\r\n报错时间：" + currentTime;

            textObject.put("content", content);
            sendParams.put("text", textObject);

            //发送钉钉
            sendDingTalk(aiMonitorDto.getMonitorDingToken(), sendParams.toString());
            AiDingTalkRecordDto aiDingTalkRecordDto = new AiDingTalkRecordDto();
            aiDingTalkRecordDto.setRecordKey(aiMonitorDto.getMonitorKey())
                    .setRecordMobile("")
                    .setRecordDesc(aiMonitorDto.getMonitorDesc())
                    .setRecordDingToken(aiMonitorDto.getMonitorDingToken())
                    .setRecordDingMsg(content);
            aiDingTalkRecordProxy.addDingTalkRecord(aiDingTalkRecordDto);
        }
    }

    /**
     * 发送钉钉消息
     *
     * @param token  钉钉群token
     * @param params 入参 {"msgtype":"text","at":"@手机号","text":{"content":"发送内容"}}
     *               钉钉机器人API：https://open-doc.dingtalk.com/microapp/serverapi2/qf2nxq
     */
    @Override
    public void sendDingTalk(String token, String params) {
        String url = DING_URL + token;
        LOG.info("DingTalk.sendDingTalkWarn url:{} params:{}", url, params);
        String result = null;
        try {
            result = HttpClientUtil.doPost(url, params);
        } catch (Exception e) {
            LOG.error("DingTalk.sendDingTalkWarn o2o-exception , more info :{}", e.getMessage());
        }
        LOG.info("DingTalk.sendDingTalkWarn response:{}", result);
    }

    @Override
    public void asynSendCheckPriceDingTalk(String classMethodName, String params, BigDecimal draftPrice, BigDecimal contractAmount) {
        List<TaskAction<?>> taskActions = new ArrayList<>();
        taskActions.add(() -> {
            //发送钉钉消息
            sendDingTalkInfo(classMethodName, params, draftPrice, contractAmount);
            return 1;
        });
        // 执行任务
        Executor.getInvokeOuterServiceFactory().asyncExecuteTask(taskActions);
    }

    @Override
    public void asynSendOrderDingTalkWarn(String classMethodName, String url, String params, Long code, String msg) {
        List<TaskAction<?>> taskActions = new ArrayList<>();
        taskActions.add(() -> {
            //发送钉钉消息
            sendOrderDingTalkWarn(classMethodName, url, params, code, msg);
            return 1;
        });
        // 执行任务
        Executor.getInvokeOuterServiceFactory().asyncExecuteTask(taskActions);
    }
}
