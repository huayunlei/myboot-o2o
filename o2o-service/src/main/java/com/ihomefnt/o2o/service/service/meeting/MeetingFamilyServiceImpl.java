package com.ihomefnt.o2o.service.service.meeting;

import com.ihomefnt.o2o.intf.domain.meeting.dto.AddMemberDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.UpdateMemberDto;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.AddMemberRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.UpdateMemberRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.AddMemberResponse;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.UpdateMemberResponse;
import com.ihomefnt.o2o.intf.proxy.meeting.MeetingFamilyProxy;
import com.ihomefnt.o2o.intf.service.meeting.MeetingFamilyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MeetingFamilyServiceImpl implements MeetingFamilyService {

	@Autowired
	private MeetingFamilyProxy meetingFamilyProxy;

	@Override
	public AddMemberResponse saveMember(AddMemberRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("familyId", request.getFamilyId());
		params.put("code", request.getCode());
		params.put("nickName", request.getNickName());
		params.put("url", request.getUrl());

		AddMemberResponse response = new AddMemberResponse();
		AddMemberDto addMemberDto = meetingFamilyProxy.saveMember(params);
		if (addMemberDto != null) {
			if (StringUtils.isNotBlank(addMemberDto.getMemberId())) {
				response.setMemberId(addMemberDto.getMemberId());
				response.setFamilyId(addMemberDto.getFamilyId());
			}
		}
		return response;
	}

	@Override
	public UpdateMemberResponse updateMember(UpdateMemberRequest request) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openId", request.getOpenId());
		params.put("nickName", request.getNickName());
		params.put("url", request.getUrl());

		UpdateMemberResponse response = new UpdateMemberResponse();
		UpdateMemberDto updateMemberDto = meetingFamilyProxy.updateMember(params);
		if (updateMemberDto != null) {
			if (StringUtils.isNotBlank(updateMemberDto.getMemberId())) {
				response.setMemberId(updateMemberDto.getMemberId());
			}
			if (StringUtils.isNotBlank(updateMemberDto.getFamilyId())) {
				response.setFamilyId(updateMemberDto.getFamilyId());
			}
		}
		return response;
	}
}
