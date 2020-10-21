/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:OrderDetailHttp.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class OrderDetailHttp {

	/**
	 * 订单明细id
	 */
	private Integer idtDetail;

	/**
	 * 订单id
	 */
	private Integer fidOrder;

	/**
	 * 套装id
	 */
	private Integer fidSuit;

	/**
	 * 空间id
	 */
	private Integer fidSuitRoom;

	/**
	 * 商品id
	 */
	private Integer fidProduct;

	/**
	 * 商品数量
	 */
	private Integer productAmount;

	/**
	 * 商品单价
	 */
	private BigDecimal productPrice;

	/**
	 * 商品总价
	 */
	private BigDecimal productAmountPrice;

	/**
	 * 厂家编号
	 */
	private String factoryNoVersion;

	/**
	 * 订单的商品状态
	 */
	private Integer state;

	/**
	 * 是否是样品
	 */
	private Integer sample;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 1、商品 2货物
	 */
	private Integer type;

	/**
	 * 快递编号
	 */
	private String deliveryNumber;

	/**
	 * 快递公司名称
	 */
	private String deliveryName;

	/**
	 * 快递公司编码
	 */
	private String deliveryCode;

	/**
	 * 删除标志位
	 */
	private Integer deteleFlag;
}
