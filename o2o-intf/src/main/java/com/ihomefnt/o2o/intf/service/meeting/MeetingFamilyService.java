package com.ihomefnt.o2o.intf.service.meeting;

import com.ihomefnt.o2o.intf.domain.meeting.vo.request.AddMemberRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.UpdateMemberRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.AddMemberResponse;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.UpdateMemberResponse;

/**
 * 家庭成员管理
 * 
 * @author czx
 */
public interface MeetingFamilyService {

	/**
	 * 添加家庭成员
	 * 
	 * @param request
	 * @return
	 */
	AddMemberResponse saveMember(AddMemberRequest request);

	/**
	 * 更新家庭成员
	 * 
	 * @param request
	 * @return
	 */
	UpdateMemberResponse updateMember(UpdateMemberRequest request);

}
