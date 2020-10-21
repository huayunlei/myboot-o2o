package com.ihomefnt.o2o.api.controller.meeting;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.AddMessageRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.QueryMessageRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.AddMessageResponse;
import com.ihomefnt.o2o.intf.service.meeting.MeetingMsgService;
import com.ihomefnt.oms.trade.util.PageModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Deprecated
@RestController
@Api(tags = "【2017跨年相关】",hidden = true)
@RequestMapping("/meetingMsg")
public class MeetingMsgController {

	@Autowired
	private MeetingMsgService meetingMsgService;

	@ApiOperation(value = "发布留言", notes = "发布留言")
	@RequestMapping(value = "/publishMsg", method = RequestMethod.POST)
	public HttpBaseResponse<AddMessageResponse> publishMsg(@RequestBody AddMessageRequest request) {
		AddMessageResponse addMessageResponse = meetingMsgService.publishMsg(request);
		return HttpBaseResponse.success(addMessageResponse);
	}

	@ApiOperation(value = "获取留言", notes = "获取留言")
	@RequestMapping(value = "/queryMsg", method = RequestMethod.POST)
	public HttpBaseResponse<PageModel> queryMsg(@RequestBody QueryMessageRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		PageModel pageModel = meetingMsgService.queryMsg(request);
		return HttpBaseResponse.success(pageModel);
	}
}
