/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月4日
 * Description:StandardUpgradeInfoVo.java 
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class StandardUpgradeInfoVo {

	@ApiModelProperty("主键标识")
	private Long batchId;

	@ApiModelProperty("sku id")
	private Integer skuId;

	@ApiModelProperty("sku name")
	private String productName;

	@ApiModelProperty("类目id")
	private Integer categoryId;

	@ApiModelProperty("类目名称")
	private String categoryName;

	@ApiModelProperty("颜色")
	private String color;

	@ApiModelProperty("升级费用")
	private BigDecimal upgradeFee;
	
	private String productImage;//商品图地址

}
