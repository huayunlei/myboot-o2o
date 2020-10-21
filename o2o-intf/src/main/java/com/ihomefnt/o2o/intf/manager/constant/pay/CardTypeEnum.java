/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月14日
 * Description:CardTypeEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.pay;

/**
 * @author zhang
 */
public enum CardTypeEnum {

	/**
	 * 卡类型 2 借记卡 3 信用卡
	 */

	CARD_DEBIT("借记卡", 2), CARD_CREDIT("信用卡", 3);

	private String msg;

	private Integer code;

	private CardTypeEnum(String msg, Integer code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(Integer code) {
		for (CardTypeEnum c : CardTypeEnum.values()) {
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
