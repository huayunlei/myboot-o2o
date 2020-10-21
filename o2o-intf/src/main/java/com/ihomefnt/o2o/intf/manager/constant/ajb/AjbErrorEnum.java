/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月8日
 * Description:AjbErrorEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.ajb;

/**
 * @author zhang
 */
public enum AjbErrorEnum {
	PUSH_ERROR("消息推送接口出现异常", -13L),
	LOG_INSERT_ERROR("该活动记录日志接口新增出现异常", -12L),
	AJB_RECHARGE_ERROR("艾积分充值出现异常", -11L),
	DATA_EMPTY("传入参数有空值,或传入参数有误", -10L),
	USER_TOKEN_EMPTY("用户未登录或登录已过期", -9L),
	SYS_ADMIN_ERROR("不是系统管理员", -8L),
	STICKY_POST_ERROR("该贴已经是精华贴", -7L),
	ACTIVITY_OVER_ERROR("该活动已经结束", -6L),
	LOG_QUERY_ERROR("该活动记录日志接口查询出现异常", -5L),
	TIMES_OVER("晒家最多获得20次艾积分奖励", -4L),
	SHARE_ORDER_USERID_ERROR("晒家用户无法获取", -3L);

	private String msg;

	private Long code;

	private AjbErrorEnum(String msg, Long code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(Long code) {
		for (AjbErrorEnum c : AjbErrorEnum.values()) {
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
