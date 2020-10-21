/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月28日
 * Description:Kuaidi100StateEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.kuaidi100;



/**
 * @author zhang
 */
public enum Kuaidi100StateEnum {
	

	// 0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单

	STATE_ON_WAY("在途中", 0), STATE_RECEIVED("已揽收", 1), STATE_DIFFICULT("疑难", 2), STATE_SIGN_IN("已签收", 3), STATE_SIGN_BACK(
			"退签", 4), STATE_DELIVERY("同城派送中", 5), STATE_GO_BACK("退回", 6), STATE_EXCHANGE("转单", 7);

	private String name;

	private int code;

	private Kuaidi100StateEnum(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	
	public static String getMsg(Integer code) {
		for (Kuaidi100StateEnum c : Kuaidi100StateEnum.values()) {
			if (c.getCode() == code) {
				return c.name;
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
