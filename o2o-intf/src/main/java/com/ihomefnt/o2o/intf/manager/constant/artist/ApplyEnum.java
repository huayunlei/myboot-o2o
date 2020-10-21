/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月21日
 * Description:ApplyEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.artist;

/**
 * @author zhang
 */
public enum ApplyEnum {

	SUCCESS("成功", 1), SUM_AMOUNT_ZERO("收益总金额为零，没有可提现金额", 2), AMOUNT_UNDER_ONE("微信不支持一元以下打款", 3), WITHDRAWAL_AMOUNT_ERROR(
			"申请提现金额超过可提现金额", 4), SYS_ERROR("呀！系统好像开小差了。再来一次唤醒它叭。", -1), USER_ERROR("用户登录已经超时,请重新登录", -2);

	private String msg;

	private Integer code;

	private ApplyEnum(String msg, Integer code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(Integer code) {
		for (ApplyEnum c : ApplyEnum.values()) {
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

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
