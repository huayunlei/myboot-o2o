/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月3日
 * Description:Test.java 
 */
package com.ihomefnt.o2o.intf.proxy.art;

/**
 * @author zhang
 */
public enum ArtOrderSourceConstant {
	

	// 订单来源,1:iPhone客户端，2:Android客户端，3:H5网站，4:PC网站，5:客服电话下单，6:客服现场下单
	// 8:微信小程序订单

	IPHONE_SOURCE("IPHONE", 1), ANDROID_SOURCE("ANDROID", 2),H5_SOURCE("H5", 3)
	,WECHAT_APPLET_SOURCC("WECHAT_APPLET", 8);

	private String name;

	private int code;

	private ArtOrderSourceConstant(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	
	public static String getMsg(Integer code) {
		for (ArtOrderSourceConstant c : ArtOrderSourceConstant.values()) {
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
