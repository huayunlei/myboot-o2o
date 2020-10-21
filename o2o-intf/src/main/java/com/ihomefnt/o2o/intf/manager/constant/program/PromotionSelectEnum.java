/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月21日
 * Description:PromotionSelectEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.program;

/**
 * @author zhang
 */
public enum PromotionSelectEnum {
	
	//是否选中:0:否，1:是
	
	NO(0, "否"),

	YES(1, "是");

	private Integer code;

	private String msg;

	PromotionSelectEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getMsg(Integer code) {
		for (PromotionSelectEnum c : PromotionSelectEnum.values()) {
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
