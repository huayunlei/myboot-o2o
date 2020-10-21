/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:HardOrderVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class HardOrderVo extends OrderDtoVo {

	/**
	 * 顾客id
	 */
	private Integer customerId;
	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 省id
	 */
	private Integer provinceId;
	/**
	 * 市id
	 */
	private Integer cityId;
	/**
	 * 区县id
	 */
	private Integer areaId;
	/**
	 * 房屋小区
	 */
	private String houseArea;
	/**
	 * 楼栋号
	 */
	private String buildingNo;
	/**
	 * 房间号
	 */
	private String roomNo;
	/**
	 * 手工单号
	 */
	private String manualNo;

	/**
	 * 已收金额(订单支付，定金，诚意金之和)
	 */
	private BigDecimal payedMoneyAll;
	/**
	 * 已收金额（订单支付金额）
	 */
	private BigDecimal payedOrderMoney;

	/**
	 * 收据编号
	 */
	private String receiptNo;

	/**
	 * 支付方式
	 */
	private Integer payType;

	/**
	 * 抵用券金额
	 */
	private BigDecimal voucher;
	/**
	 * 现金券金额
	 */
	private BigDecimal cashCoupon;

	/**
	 * 诚意金
	 */
	private BigDecimal earnestMoney;

	/**
	 * 定金
	 */
	private BigDecimal depositMoney;
	// 财务属性
	private Integer financial;
}
