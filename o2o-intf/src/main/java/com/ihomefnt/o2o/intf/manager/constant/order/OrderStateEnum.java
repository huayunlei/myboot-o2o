/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:OrderStateEnum.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.order;

/**
 * @author zhang
 */
public enum OrderStateEnum {

	/**
	 * 订单原状态含义
	 * CREATE("提交订单", 0), PROCESSING("处理中", 1), FINISH("已完成", 2), CANCEL("已取消", 3), NO_PAYMENT("待付款", 4), NO_RECEIVE(
	 * 			"待收货", 5), PART_PAYMENT("部分付款", 6), NO_CONSTRUCT("待施工", 7), CONSTRUCTING("施工中", 8), NO_REFUND("待退款", 9), NO_SEND(
	 * 			"待发货", 10), NO_FOR_PAYMENT("待结款", 11), CLOSED("交易关闭", 12), WAIT_ORDER("待接单", 13), PART_SEND("部分发货", 14);
	 */

	//app端展示含义
	CREATE("提交订单", 0), PROCESSING("处理中", 1), FINISH("已完成", 2), CANCEL("已取消", 3), NO_PAYMENT("待付款", 4), NO_RECEIVE(
			"已发货", 5), PART_PAYMENT("部分付款", 6), NO_CONSTRUCT("待施工", 7), CONSTRUCTING("施工中", 8), NO_REFUND("待退款", 9), NO_SEND(
			"已开始定制", 10), NO_FOR_PAYMENT("待结款", 11), CLOSED("交易关闭", 12), WAIT_ORDER("已付款", 13), PART_SEND("已发货", 14);

	private String name;
	private int code;

	private OrderStateEnum(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public static OrderStateEnum get(int code) {
		for (OrderStateEnum c : OrderStateEnum.values()) {
			if (c.getCode() == code) {
				return c;
			}
		}
		return null;
	}

	public static String getName(int code) {
		for (OrderStateEnum c : OrderStateEnum.values()) {
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
