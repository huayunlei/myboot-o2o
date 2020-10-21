/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月3日
 * Description:StandardUpgradeSkuResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class StandardUpgradeSkuResponseVo implements Serializable{

	private Integer skuId;// sku id

	private String pvsId;// sku 规格id

	private String pvsName;// sku 规格信息（颜色）

	private String pvsValue;// sku 规格值（颜色图片url）

	private BigDecimal upgradeFee;// 升级费用

	private BigDecimal upgradeCostFee;// 升级成本费用,

	private BigDecimal discountUpgradeFee;// 升级费用(折扣)

}
