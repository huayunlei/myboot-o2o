package com.ihomefnt.o2o.intf.domain.meeting.dto;

import lombok.Data;

/**
 * 家庭信息
 * @author ZHAO
 */
@Data
public class FamilyInfoDto {
	private Integer familyId;//家庭ID
	
	private Integer managerId;//艾管家ID
	
	private String company;//所属公司
	
	private String project;//所属楼盘
	
	private String orderNum;//订单编号
	
	private String phone;//家庭手机号
	
	private String familyName;//家庭名称

	private String familyUrl;//家庭头像

	private Integer sort;//排序
}
