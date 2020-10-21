package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

/**
 * 登陆返回结果
 * 
 * @author Ivan
 * @date 2016年7月13日 上午10:28:06
 */
@Data
public class LoginResultVo {

	/**
	 * 用户登陆成功标识
	 */
	private String token;

	/**
	 * 是否注册成功
	 */
	private boolean success;

	/**
	 * 返回代码
	 * 
	 */
	private Integer code;

	/**
	 * 返回信息
	 */
	private String msg;

}
