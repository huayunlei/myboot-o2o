/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月20日
 * Description:PromotionStatusEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.program;

/**
 * @author zhang
 */
public enum PromotionStatusEnum {

	/**
	 * 活动状态:1 即将结束 2.正在进行 3.即将开始 4.已参与
	 * 
	 */

	FINISHING(1, "活动即将结束"),

	DOING(2, "活动正在进行"),

	WAITING(3, "活动即将开始"),

	SUCCESS_DONE(4, "活动已参与");

	private Integer code;

	private String msg;

	PromotionStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getMsg(Integer code) {
		for (PromotionStatusEnum c : PromotionStatusEnum.values()) {
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
