/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:TripOrderCreateDto.java
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class TripOrderCreateDto extends OrderCreateHttp {

	// 艾积分
	private BigDecimal ajbMoney;
}
