package com.ihomefnt.o2o.api.controller.push;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.push.dto.ExtrasDto;
import com.ihomefnt.o2o.intf.domain.push.dto.JpushUpdateRequestVo;
import com.ihomefnt.o2o.intf.domain.push.vo.response.PullPersonalMessageListResponseVo;
import com.ihomefnt.o2o.intf.service.push.PushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
@ApiIgnore
@Api(tags = "【消息推送】API",hidden = true)
@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    PushService pushService;
    
    @ApiOperation(value = "拉取个人未送达jpush信息", notes = "/push/pullPersonalMessageList")
    @RequestMapping(value = "/pullPersonalMessageList", method = RequestMethod.POST)
    public HttpBaseResponse<PullPersonalMessageListResponseVo> pullPersonalMessageList(@RequestBody HttpBaseRequest request) {
    	List<ExtrasDto> list = pushService.queryPushList(request);
    	return HttpBaseResponse.success(new PullPersonalMessageListResponseVo(list));
    }  
    
    @ApiOperation(value = "通知服务端更新个人jpush信息为已送达", notes = "/push/updatePersonalMessageList")
    @RequestMapping(value = "/updatePersonalMessageList", method = RequestMethod.POST)
    public HttpBaseResponse<Void> updatePersonalMessageList(@RequestBody JpushUpdateRequestVo request) {
        if (request == null||CollectionUtils.isEmpty(request.getMsgIdList())||StringUtils.isBlank(request.getMobileNum())
        		||StringUtils.isBlank(request.getAppVersion())) {
        	return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        pushService.updatePushList(request);
        return HttpBaseResponse.success(null);
    } 
     
}
