/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:TripOrderVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class TripOrderVo extends OrderDtoVo {

	// 艾积分
	private BigDecimal ajbMoney;
}
