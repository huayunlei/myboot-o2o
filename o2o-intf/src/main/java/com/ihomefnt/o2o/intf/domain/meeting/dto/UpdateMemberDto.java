package com.ihomefnt.o2o.intf.domain.meeting.dto;

import lombok.Data;

/**
 * 修改家庭成员
 * 
 * @author czx
 */
@Data
public class UpdateMemberDto{
	
	private String memberId; // 成员 ID
	
	private String familyId;// 家庭 ID
}
