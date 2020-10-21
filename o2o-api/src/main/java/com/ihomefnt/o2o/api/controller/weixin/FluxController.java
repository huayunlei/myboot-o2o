/**
 *
 */
package com.ihomefnt.o2o.api.controller.weixin;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FLuxAccessToken;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxActivity;
import com.ihomefnt.o2o.intf.domain.weixin.dto.FluxUser;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.HttpFluxLogRequest;
import com.ihomefnt.o2o.intf.manager.constant.weixin.FluxConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.manager.util.common.secure.XMLUtil;
import com.ihomefnt.o2o.intf.manager.util.common.wechat.Weixinutil;
import com.ihomefnt.o2o.intf.service.weixin.FluxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhang 原始需求：微信活动领取流量<br/>
 *         http://wiki.ihomefnt.com:8002/pages/viewpage.action?pageId=4627635<br/>
 */
@ApiIgnore
@Deprecated
@Api(tags = "微信领流量API", hidden = true)
@RestController
@RequestMapping(value = "/flux")
public class FluxController {

    private static final Logger LOG = LoggerFactory.getLogger(FluxController.class);

    @Autowired
    private FluxService fluxService;

    /**
     * 领取流量
     */
    @ApiOperation(value = "acceptFlux", notes = "领取流量")
    @RequestMapping(value = "/acceptFlux", method = RequestMethod.POST)
    public HttpBaseResponse<Integer> acceptFlux(@Json HttpFluxLogRequest request) {
        if (request == null || StringUtils.isBlank(request.getMobile()) || StringUtils.isBlank(request.getUnionId())) {
            return HttpBaseResponse.fail(FluxConstant.CODE_DATA_EMPTY, FluxConstant.MESSAGE_DATA_EMPTY);
        }
        FluxActivity activity = fluxService.queryActivityByPK(FluxConstant.CODE_FLUX_ACTIVITY);
        // 判断活动是否有效
        if (activity == null || activity.getBeginTime() == null
                || activity.getEndTime() == null) {
            return HttpBaseResponse.fail(FluxConstant.CODE_ACCPETED_EXCEPTION, FluxConstant.MESSAGE_ACCPETED_EXCEPTION);
        }
        Timestamp beginTime = activity.getBeginTime();
        // 判断活动是否开始
        if (beginTime.after(new Timestamp(System.currentTimeMillis()))) {
            return HttpBaseResponse.fail(FluxConstant.CODE_NOT_START, FluxConstant.MESSAGE_NOT_START);
        }
        Timestamp endTime = activity.getEndTime();
        // 判断活动是否结束
        if (endTime.before(new Timestamp(System.currentTimeMillis()))) {
            return HttpBaseResponse.fail(FluxConstant.CODE_FINISHED, FluxConstant.MESSAGE_FINISHED);
        }
        // 判断用户号码判断是否已经领取
        int count = fluxService.queryLogByConditon(request);
        if (count > 0) {
            return HttpBaseResponse.fail(FluxConstant.CODE_ACCPETED, FluxConstant.MESSAGE_ACCPETED);
        }
        List<FluxUser> list = fluxService.queryUserByConditon(request);
        // 判断用户是否关注
        if (list == null || list.isEmpty()) {
            return HttpBaseResponse.fail(FluxConstant.CODE_NOT_FOCUS, FluxConstant.MESSAGE_NOT_FOCUS);
        }
        // 判断用户是否领取成功
        int mobileType = fluxService.acceptFlux(request);
        return HttpBaseResponse.success(mobileType);
    }

    /**
     * 七夕活动
     */
    @ApiOperation(value = "qixi", notes = "七夕活动")
    @RequestMapping(value = "/qixi", method = RequestMethod.POST)
    public HttpBaseResponse<Integer> qixi(@Json HttpFluxLogRequest request) {
        if (request == null || StringUtils.isBlank(request.getUnionId())) {
            return HttpBaseResponse.fail(FluxConstant.CODE_DATA_EMPTY, FluxConstant.MESSAGE_DATA_EMPTY);
        }
        List<FluxUser> list = fluxService.queryUserByConditon(request);

        int focusStatus = FluxConstant.STATUS_FOCUS;
        if (list == null || list.isEmpty()) {
            // 用户没有关注
            focusStatus = FluxConstant.STATU_FOCUS_NOT;
        }
        return HttpBaseResponse.success(focusStatus);
    }

    /**
     * 查询微信token
     *
     */
    @ApiOperation(value = "queryAccessToken", notes = "查询微信token")
    @RequestMapping(value = "/queryAccessToken", method = RequestMethod.POST)
    public HttpBaseResponse<FLuxAccessToken> queryAccessToken(@Json HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(FluxConstant.CODE_DATA_EMPTY, FluxConstant.MESSAGE_DATA_EMPTY);
        }
        return HttpBaseResponse.success(fluxService.getFLuxAccessToken());
    }

    /**
     * 查询微信token
     */
    @ApiOperation(value = "queryUserList", notes = "queryUserList")
    @RequestMapping(value = "/queryUserList", method = RequestMethod.POST)
    public HttpBaseResponse<List<FluxUser>> queryUserList(@Json HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(FluxConstant.CODE_DATA_EMPTY, FluxConstant.MESSAGE_DATA_EMPTY);
        }
        return HttpBaseResponse.success(fluxService.getFluxUserList());
    }

    /**
     * 查询活动
     */
    @ApiOperation(value = "queryActivity", notes = "查询活动")
    @RequestMapping(value = "/queryActivity", method = RequestMethod.POST)
    public HttpBaseResponse<FluxActivity> queryActivity(@Json HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(FluxConstant.CODE_DATA_EMPTY, FluxConstant.MESSAGE_DATA_EMPTY);
        }
        return HttpBaseResponse.success(fluxService.queryActivityByPK(FluxConstant.CODE_FLUX_ACTIVITY));
    }

    /**
     * 微信异步回调<br/>
     * 此方法为微信回调，故尽量多打日志，方便出了异常快速定位<br/>
     * 如果不懂，多看看微信开发平台<br/>
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "callback", notes = "微信异步回调")
    @RequestMapping(value = "/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.info("FluxController callback ");

        //根据微信开发平台，进行鉴权
        boolean checkResult = Weixinutil.checkSignature(request);
        //查看是否通过微信验证
        LOG.info("callback checkResult:" + checkResult);
        PrintWriter out = response.getWriter();
        //如果鉴权通过，务必告诉给微信
        if (checkResult) {
            String echostr = request.getParameter("echostr");
            //表示鉴权通过，通知给微信
            out.print(echostr);
        }
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            outStream.close();
            String result = new String(outStream.toByteArray(), "utf-8");
            //查看微信传递过来的内容
            LOG.info("callback content from weixin:" + result);
            //保护一下，防止weixin那边空返回
            if (StringUtils.isNotBlank(result)) {
                Map<Object, Object> requestMap = XMLUtil.doXMLParse(result);
                // FromUserName  发送方帐号（一个OpenID）
                String openId = (String) requestMap.get("FromUserName");
                // MsgType  消息类型，
                String msgType = (String) requestMap.get("MsgType");
                // 关注/取消关注事件   MsgType 为 event
                if (msgType.equals("event")) {
                    //Event  事件类型，subscribe(订阅)、unsubscribe(取消订阅)
                    String eventType = (String) requestMap.get("Event");
                    //订阅
                    if (eventType.equals("subscribe")) {
                        fluxService.addOrUpdateFluxUser(openId, FluxConstant.STATUS_FOCUS);//关注类型：1 关注
                        sendMessage(openId);
                    }
                    //取消订阅
                    else if (eventType.equals("unsubscribe")) {
                        fluxService.addOrUpdateFluxUser(openId, FluxConstant.STATU_FOCUS_NOT);//关注类型： 0 非关注
                    }
                    //其他事件，暂不处理
                } else if (msgType.equals("text")) {
                    String content = "你的七夕情人节礼物竟然是这个，戳链接领取吧：http://m.ihomefnt.com/love/homecard";
                    //String content = "终于等到你！百万流量等你来抢，戳链接领取:http://m.ihomefnt.com/flowrate/home";
                    long timestamp = new Date().getTime();
                    String resCont = "<xml>" + "<ToUserName><![CDATA[" + openId + "]]></ToUserName>"
                            + "<FromUserName><![CDATA[gh_0c28fa2c2ab0]]></FromUserName>"
                            + "<CreateTime>" + timestamp + "</CreateTime>"
                            + "<MsgType><![CDATA[text]]></MsgType>"
                            + "<Content><![CDATA[" + content + "]]></Content>"
                            + "</xml>";

                    out.write(resCont);
                    out.flush();
                }
            }
        } catch (Exception e) {
            LOG.error("flux.callback o2o-exception , more info :{}", e.getMessage());
        } finally {
            LOG.info("call back end");
            inStream.close();
            out.close();
            outStream.close();
        }
    }

    /**
     * 发送消息
     * @param openId
     * @throws IOException
     * @throws ClientProtocolException
     */
    private void sendMessage(String openId) throws ClientProtocolException, IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
        String accessToken = fluxService.getFLuxAccessToken()
                .getAccessToken();
        url = url.replaceAll("ACCESS_TOKEN", accessToken);
        LOG.info(url);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String strJson = "{\"touser\" :\"" + openId + "\",";
        strJson += "\"msgtype\":\"text\",";
        strJson += "\"text\":{";
        strJson += "\"content\":\"你的七夕情人节礼物竟然是这个，戳链接领取吧：http://m.ihomefnt.com/love/homecard\"";
        //strJson += "\"content\":\"终于等到你！百万流量等你来抢，戳链接领取:http://m.ihomefnt.com/flowrate/home\"";
        strJson += "}}";
        LOG.info(strJson);
        StringEntity entity = new StringEntity(strJson, "utf-8");// 解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        HttpPost me = new HttpPost(url);
        me.setEntity(entity);
        httpClient.execute(me);
        me.releaseConnection();
    }

}
