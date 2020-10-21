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
public enum ArtistRegisterErrorEnum {

	PHONE_ERROR("请输入正确手机号", -9L), 
	SMS_ERROR("请输入短信验证码", -8L), 
	OPEN_ID_ERROR("很抱歉，未能绑定您的微信，请返回重试。", -7L), 
	NAME_ERROR("请输入姓名", -6L), 
	DATA_EMPTY("传入参数有空值,或传入参数有误", -5L), 
	DEFAULT_ERROR("系统错误，请联系艾佳生活", -1L), 	
	OK("注册成功", 0L), 
	SYS_ERROR("呀！系统好像开小差了。再来一次唤醒它叭。", 3L), 
	REGISTER_ERROR("您已经申请过，留意短信通知", 4L), 
	APPLY_ERROR("您已经申请通过啦，请在微信小程序中“艾佳生活DNA设计师”登录使用", 5L), 
	WEIXIN_APPLY_ERROR("您的手机号已经申请过啦，同一个手机号不能再次注册。", 6L), 
	WEIXIN_ERROR("您的微信号已经申请过啦，同一个微信号不能再次注册。", 7L), 
	DONE_ERROR("呀！系统好像开小差了。再来一次唤醒它叭。", 8L), 
	ACCOUNT_ERROR("账号已存在，请联系艾佳生活", 9L), 
	SMSCODE_ERROR("验证码错误", 10L), 
	SMSCODE_AFRESH("请输入短信验证码", 11L), 
	SMSCODE_TIMEOUT("请重新获取短信验证码", 12L);

	private String msg;

	private Long code;

	private ArtistRegisterErrorEnum(String msg, Long code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(Long code) {
		for (ArtistRegisterErrorEnum c : ArtistRegisterErrorEnum.values()) {
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
