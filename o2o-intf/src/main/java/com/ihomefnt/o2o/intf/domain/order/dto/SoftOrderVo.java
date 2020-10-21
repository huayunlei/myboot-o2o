/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:SoftOrderVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class SoftOrderVo extends OrderDtoVo {

	// 线下订单属性，线上订单可不传

	/**
	 * 软装合同编号
	 */
	private String softOrderNum;

	/**
	 * 楼栋号
	 */
	private String buildingNo;

	/**
	 * 房屋号
	 */
	private String houseNo;

	/**
	 * 财务属性
	 */
	private Integer financial;

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

	/**
	 * 抵用券id
	 */
	private Integer voucherId;

	/**
	 * 定金id
	 * 
	 * @return
	 */
	private Integer depositId;

	/**
	 * 诚意金id
	 * 
	 * @return
	 */
	private Integer earnestId;

	/**
	 * 客户id
	 * 
	 * @return
	 */
	private Integer customerId;
}
