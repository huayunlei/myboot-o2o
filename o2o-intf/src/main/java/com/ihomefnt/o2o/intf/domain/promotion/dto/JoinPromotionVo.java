/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月19日
 * Description:JoinPromotionVo.java 
 */
package com.ihomefnt.o2o.intf.domain.promotion.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class JoinPromotionVo {

	private Integer orderNum;// 订单ID

	private Integer companyId;// 所属公司

	private Integer buildingId;// 所属楼盘

	private Integer orderSource;// 订单来源:1:BOSS,2:APP

	private String customerName;// 客户姓名

	private BigDecimal contractAmount;// 合同金额

	private List<Integer> actCodes;// 要参加的活动列表

}
