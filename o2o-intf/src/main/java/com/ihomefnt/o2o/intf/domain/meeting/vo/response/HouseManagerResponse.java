package com.ihomefnt.o2o.intf.domain.meeting.vo.response;

import lombok.Data;

/**
 * 艾管家
 * @author ZHAO
 */
@Data
public class HouseManagerResponse {
	private Integer managerId;//管家ID
	 
	private String managerName;//管家名称
	 
	private String nickName;//管家别名
	 
	private String url;//管家头像
	 
	private String phone;//手机号
	 
	private String description;//个人描述

	public HouseManagerResponse() {
		this.managerId = -1;
		this.managerName = "";
		this.nickName = "";
		this.url = "";
		this.phone = "";
		this.description = "";
	}

}
