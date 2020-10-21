/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月14日
 * Description:BankStatusEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.pay;

/**
 * @author zhang
 */
public enum BankStatusEnum {
	/**
	 * 银行状态 0 正常 2 维护中
	 */

	STATUS_NORMAL("正常", 0), STATUS_MAINTAIN("维护中", 2);

	private String msg;

	private Integer code;

	private BankStatusEnum(String msg, Integer code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(Integer code) {
		for (BankStatusEnum c : BankStatusEnum.values()) {
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
