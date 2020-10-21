/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月13日
 * Description:StatusEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.artist;

/**
 * @author zhang
 */
public enum StatusEnum {

	// 状态:0.待审核1.待付款2.付款中3.已付款4.已驳回,
	WAIT_APPROVE("审核中", 0), WAIT_PAY("审核中", 1), PAYING("审核中", 2), PAYED("已到账", 3), CANCLE("驳回", 4);

	private String msg;

	private Integer code;

	private StatusEnum(String msg, Integer code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(Integer code) {
		for (StatusEnum c : StatusEnum.values()) {
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
