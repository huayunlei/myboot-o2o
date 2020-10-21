/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:FamilyOrderVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class FamilyOrderVo extends OrderDtoVo {

	/**
	 * 项目id
	 */
	private Integer projectId;
	/**
	 * 房屋小区
	 */
	private String houseAddress;
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
	 * 硬装合同编号
	 */
	private String hardOrderNum;
	/**
	 * 软装合同编号
	 */
	private String softOrderNum;
	/**
	 * 硬装合同金额
	 */
	private BigDecimal hardLoadingMoney;
	/**
	 * 软装合同金额
	 */
	private BigDecimal softLoadingMoney;
	// 硬装待确认金额
	private BigDecimal hardConfirmingMoney;
	// 软装待确认金额
	private BigDecimal softConfirmingMoney;
	// 硬装已确认金额
	private BigDecimal hardConfirmedMoney;
	// 软装已确认金额
	private BigDecimal softConfirmedMoney;
	/**
	 * 抵用券金额
	 */
	private BigDecimal voucher;
	/**
	 * 现金券金额
	 */
	private BigDecimal cashCoupon;

	/**
	 * 所属销售
	 */
	private Integer saleId;
	// 客户id
	private Integer customerId;

}
