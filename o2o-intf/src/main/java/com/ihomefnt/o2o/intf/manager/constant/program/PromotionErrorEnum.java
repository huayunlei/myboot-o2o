/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月20日
 * Description:PromotionErrorEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.program;


/**
 * @author zhang
 */
public enum PromotionErrorEnum {

	ACT_NOT_EXSIST(1000, "有活动已下线,请重新选择"),

	ACT_MUTEX(1001, "存在互斥活动,请重新选择"),

	ACT_AVAIL_FAIL(1002, "活动参数错误"),

	ACT_REWARD_FAIL(1003, "活动内容已更新,请重新选择"),
	
	ACT_CANCLE_FAIL(1004, "已支付全款不允许取消活动"),

	SYS_FAIL(-1, "系统错误"),

	DEFAULT_FAIL(-2, "服务器正忙"),

	SERVICE_FAIL(-3, "服务出现异常"),

	ORDER_FAIL(-4, "订单校验失败"),

	SUCCESS(1, "操作成功");
	
	private Integer code;
	
	private String msg;

	PromotionErrorEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getMsg(Integer code) {
		for (PromotionErrorEnum c : PromotionErrorEnum.values()) {
			if (c.getCode().equals(code)) {
				return c.msg;
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
