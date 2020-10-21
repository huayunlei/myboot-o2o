/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年7月27日
 * Description:HardOrderConstant.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.lechange;

/**
 * @author zhang
 */
public enum HardOrderConstant {
	// 施工状态 0-准备开工 1-开工交底 2-水电验收 3-瓦木验收 4-竣工验收 5施工完成,
	// 0-准备开工 1-开工交底 2-水电验收 3-客户自施工项目阶段 4-瓦木验收 5-竣工验收 6-施工完成
	READY_START("准备开工", "0"),
	COMMENCEMENT_NOTICE("开工交底", "1"),
	HYDROPOWER_ACCEPTANCE("水电阶段", "2"),
	WOOD_ACCEPTANCE("客户自施工项目阶段", "3"),
	CUSTOM_ITEM_CHECK("瓦木阶段", "4"),
	FINAL_ACCEPTANCE("竣工阶段", "5"),
	CONSTRUCTION_FINISH("施工完成", "6");

	private String msg;

	private String code;

	HardOrderConstant(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(String code) {
		for (HardOrderConstant c : HardOrderConstant.values()) {
			if (c.getCode().equals(code)) {
				return c.msg;
			}
		}
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}

}
