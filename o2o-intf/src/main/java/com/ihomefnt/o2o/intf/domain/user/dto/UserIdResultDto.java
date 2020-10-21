package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

@Data
public class UserIdResultDto {

	private Integer userId;// 用户id
	
	private String token;
	
	/**
	 * 是否注册成功
	 */
	private boolean success;
	
	/**
	 * 返回代码
	 * com.ihomefnt.user.constant.UserConstant.REGISTER
	 */
	private Integer code;
	
	/**
	 * 返回信息
	 */
	private String msg;


}
