/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:ArtOrderVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhang
 */
@Data
public class ArtOrderVo extends OrderDtoVo {

	// 艺术品合同编号
	private String artOrderNum;

	// 楼栋号
	private String buildingNo;

	// 房屋号
	private String houseNo;

	// 财务属性
	private Integer financial;

	// 抵用券金额
	private BigDecimal voucher;
	// 现金券金额
	private BigDecimal cashCoupon;

	// 诚意金
	private BigDecimal earnestMoney;

	// 定金
	private BigDecimal depositMoney;

	// 艾佳券
	private BigDecimal ajbMoney;

	// 抵用券id
	private Integer voucherId;

	// 定金id
	private Integer depositId;

	// 诚意金id
	private Integer earnestId;

	// 客户id
	private Integer customerId;

	// 发货时间
	private Date deliveryDate;
}
