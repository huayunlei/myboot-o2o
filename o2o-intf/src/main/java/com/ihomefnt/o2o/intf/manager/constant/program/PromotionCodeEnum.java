/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月20日
 * Description:PromotionCodeEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.program;

/**
 * @author zhang
 */
public enum PromotionCodeEnum {

	/**
	 * 促销活动 <br/>
	 * 已付金额 ＜ 订单总价 && 订单下没有“参加成功”的活动<br/>
	 * 1: 未选择活动:待开始活动数==0 && 正在进行活动数==0 <br/>
	 * 2: 未选择活动:正在进行活动数==0 && 待开始活动数>=1 <br/>
	 * 3: 未选择活动:正在进行活动数>=1 <br/>
	 * 4: 已选择活动 <br/>
	 * 已付金额 ≥ 订单总价 && 订单下没有“参加成功”的活动<br/>
	 * 5:没有参加过活动<br/>
	 * 订单下有“参加成功”的活动 <br/>
	 * 6:参加过活动<br/>
	 */

	NOT_EXIST(1, "活动不存在"),

	WAITING(2, "活动待开始"),

	DOING(3, "活动正在进行"),

	SELECTED(4, "已选择活动"),

	NO_JOINED(5, "没有参加过活动"),

	SUCCESS_JOINED(6, "参加过活动");

	private Integer code;

	private String msg;

	PromotionCodeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getMsg(Integer code) {
		for (PromotionCodeEnum c : PromotionCodeEnum.values()) {
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
