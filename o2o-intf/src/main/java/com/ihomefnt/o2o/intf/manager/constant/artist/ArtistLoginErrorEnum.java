/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月16日
 * Description:ArtistLoginErrorEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.artist;

/**
 * @author zhang
 */
public enum ArtistLoginErrorEnum {

	 USER_APPROVED_FAILED_ERROR("审核被驳回,请重新申请", -10L), 
	 PHONE_ERROR("请输入正确手机号", -9L), 
	 SMS_ERROR("请输入验证码", -8L), 
	 USER_NOT_APPROVED_ERROR("您还在审核中，请留意短信通知", -7L), 
	 USER_NOT_EXIST_ERROR("您还未注册,请申请注册", -6L), 
	 SYS_ERROR("呀！系统好像开小差了。再来一次唤醒它叭。", -3L), 
	 DEFAULT_ERROR("系统错误，请联系艾佳生活",-2L), 
	 DATA_EMPTY("传入参数有空值,或传入参数有误", -1L), 
	 ERROR("登陆失败，用户名或短信验证码错误", 0L), 
	 OK("登陆成功", 1L), 
	 SMSCODE_ERROR("短信验证码错误", 2L), 
	 SMSCODE_AFRESH("请输入短信验证码", 3L), 
	 SMSCODE_TIMEOUT("请重新获取短信验证码", 4L);

	private String msg;

	private Long code;

	private ArtistLoginErrorEnum(String msg, Long code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(Long code) {
		for (ArtistLoginErrorEnum c : ArtistLoginErrorEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.msg;
			}
		}
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

}
