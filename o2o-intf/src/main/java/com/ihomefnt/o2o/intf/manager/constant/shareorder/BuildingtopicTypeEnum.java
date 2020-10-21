/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月8日
 * Description:BuildingtopicTypeEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.shareorder;

/**
 * @author zhang
 */
public enum BuildingtopicTypeEnum {

	TYPE_DRAFT_TOPIC("专题", 2),

	TYPE_STORY_TOPIC("文章", 3);

	private String msg;

	private int code;

	private BuildingtopicTypeEnum(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}

	public static String getMsg(int code) {
		for (BuildingtopicTypeEnum c : BuildingtopicTypeEnum.values()) {
			if (c.getCode() == code) {
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
