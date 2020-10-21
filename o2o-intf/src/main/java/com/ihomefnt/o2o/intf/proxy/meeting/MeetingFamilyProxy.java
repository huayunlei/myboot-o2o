package com.ihomefnt.o2o.intf.proxy.meeting;

import java.util.Map;

import com.ihomefnt.o2o.intf.domain.meeting.dto.AddMemberDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.UpdateMemberDto;

public interface MeetingFamilyProxy {

	/**
	 * 添加家庭成员
	 * 
	 * @param params
	 * @return
	 */
	AddMemberDto saveMember(Map<String, Object> params);

	/**
	 * 更新家庭成员
	 * 
	 * @param params
	 * @return
	 */
	UpdateMemberDto updateMember(Map<String, Object> params);
}
