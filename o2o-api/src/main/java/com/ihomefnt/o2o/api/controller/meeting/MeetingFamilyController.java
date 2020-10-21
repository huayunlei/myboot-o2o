package com.ihomefnt.o2o.api.controller.meeting;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.AddMemberRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.UpdateMemberRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.AddMemberResponse;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.UpdateMemberResponse;
import com.ihomefnt.o2o.intf.service.meeting.MeetingFamilyService;
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
@RequestMapping("/meetingFamily")
public class MeetingFamilyController {
	
	@Autowired
	private MeetingFamilyService meetingFamilyService;

	@ApiOperation(value = "添加家庭成员", notes = "添加家庭成员")
	@RequestMapping(value = "/saveMember", method = RequestMethod.POST)
	public HttpBaseResponse<AddMemberResponse> saveMember(@RequestBody AddMemberRequest request) {
		AddMemberResponse addMemberResponse = meetingFamilyService.saveMember(request);
		return HttpBaseResponse.success(addMemberResponse);
	}

	@ApiOperation(value = "更新家庭成员", notes = "更新家庭成员")
	@RequestMapping(value = "/updateMember", method = RequestMethod.POST)
	public HttpBaseResponse<UpdateMemberResponse> updateMember(@RequestBody UpdateMemberRequest request) {
		UpdateMemberResponse updateMemberResponse = meetingFamilyService.updateMember(request);
		return HttpBaseResponse.success(updateMemberResponse);
	}
}
