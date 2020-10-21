/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月11日
 * Description:OrderAttrValueDtoVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class OrderAttrValueDtoVo {

	/**
	 * 主键
	 */
	private Integer idOrderAttrvalue;

	/**
	 * 订单id
	 */
	private Integer fidOrder;

	/**
	 * 订单属性
	 */
	private Integer orderType;

	/**
	 * 属性id
	 */
	private Integer fidtOrderAttr;

	/**
	 * 属性名
	 */
	private String attrName;

	/**
	 * 属性值
	 */
	private String attrValue;
}
