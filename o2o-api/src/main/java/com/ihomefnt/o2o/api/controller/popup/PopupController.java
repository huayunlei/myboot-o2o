package com.ihomefnt.o2o.api.controller.popup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ihomefnt.o2o.intf.domain.popup.vo.response.PopupResponseVo;
import com.ihomefnt.o2o.intf.service.popup.PopupService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.right.vo.request.OrderRightPopupRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author huayunlei
 * @created 2018年12月6日 上午11:23:40
 * @desc 
 */
@RestController
@RequestMapping(value = "/popup")
@Api(tags="【弹框API】")
public class PopupController {
	
	@Autowired
	private PopupService popupService;
	
	@ApiOperation(value = "判断权益弹窗-v5.2.0", notes = "判断权益弹窗-v5.2.0")
    @RequestMapping(value = "/judgeRightPopup",method = RequestMethod.POST)
    public HttpBaseResponse<PopupResponseVo> judgeRightPopup(@RequestBody OrderRightPopupRequest request){
		if (request == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		
		PopupResponseVo response = popupService.judgeRightPopup(request);
		return HttpBaseResponse.success(response);
	}

	
	@ApiOperation(value = "取消权益弹框", notes = "取消权益弹框")
    @RequestMapping(value = "/cancelRightPopup",method = RequestMethod.POST)
    public HttpBaseResponse<Void> cancelRightPopup(@RequestBody OrderRightPopupRequest request){
		if (request == null || null == request.getOrderNum()) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
    	popupService.cancelRightPopup(request);
    	return HttpBaseResponse.success();
	}
}
