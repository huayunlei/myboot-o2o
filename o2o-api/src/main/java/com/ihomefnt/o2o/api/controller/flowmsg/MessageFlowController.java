package com.ihomefnt.o2o.api.controller.flowmsg;

import com.alibaba.fastjson.JSON;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBasePageResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.flowmsg.vo.request.QueryMessageRecordFlowRequestVo;
import com.ihomefnt.o2o.intf.domain.flowmsg.vo.response.MessageCardInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.flowmsg.vo.response.MessageRecordFlowResponseVo;
import com.ihomefnt.o2o.intf.proxy.flowmsg.MessageFlowProxy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-21 10:52
 */
@RestController
@RequestMapping("/message")
@Api(tags = "【信息流API】")
public class MessageFlowController {

    @Autowired
    private MessageFlowProxy messageFlowProxy;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "查询订单信息流", notes = "查询信息流")
    @PostMapping("/getMessageFlowList")
    public HttpBasePageResponse<MessageCardInfoResponseVo> getMessageFlowList(@RequestBody QueryMessageRecordFlowRequestVo request) {
        HttpBasePageResponse<MessageCardInfoResponseVo> basePageResponse = new HttpBasePageResponse<>();
        basePageResponse.setPageSize(request.getPageSize());
        basePageResponse.setPageNo(request.getPageNo());

        if (null != request.getUserInfo()) {
            request.setUserId(request.getUserInfo().getId());
        }

        MessageRecordFlowResponseVo responseDto = messageFlowProxy.getMessageFlowList(request);
        if (null != responseDto) {
            basePageResponse.setObj(responseDto.getFeedMessage());
            basePageResponse.setTotalCount(responseDto.getTotalCount());
        }

        basePageResponse.setExt("查询成功");
        basePageResponse.setCode(HttpResponseCode.SUCCESS);
        return basePageResponse;
    }

    @ApiOperation(value = "查询默认信息流", notes = "查询信息流")
    @PostMapping("/getDefaultMessageFlowList")
    public HttpBasePageResponse<MessageCardInfoResponseVo> getDefaultMessageFlowList(@RequestBody QueryMessageRecordFlowRequestVo request) {
        HttpBasePageResponse<MessageCardInfoResponseVo> basePageResponse = new HttpBasePageResponse<>();
        basePageResponse.setPageSize(request.getPageSize());
        basePageResponse.setPageNo(request.getPageNo());

        if (null != request.getUserInfo()) {
            request.setUserId(null);
        }
        MessageRecordFlowResponseVo responseDto;
        String appHomeMessageFlowForDefault = stringRedisTemplate.opsForValue().get("APP_HOME_MESSAGE_FLOW_FOR_DEFAULT");
        if (StringUtils.isNotBlank(appHomeMessageFlowForDefault)) {
            responseDto = JSON.parseObject(appHomeMessageFlowForDefault, MessageRecordFlowResponseVo.class);
        } else {
            responseDto = messageFlowProxy.getMessageFlowList(request);
            if (responseDto != null) {
                stringRedisTemplate.opsForValue().set("APP_HOME_MESSAGE_FLOW_FOR_DEFAULT", JSON.toJSONString(responseDto), 2, TimeUnit.HOURS);
            }
        }
        if (null != responseDto && CollectionUtils.isNotEmpty(responseDto.getFeedMessage())) {
            basePageResponse.setObj(responseDto.getFeedMessage());
            basePageResponse.setTotalCount(responseDto.getTotalCount());
        }

        basePageResponse.setExt("查询成功");
        basePageResponse.setCode(HttpResponseCode.SUCCESS);
        return basePageResponse;
    }

}
