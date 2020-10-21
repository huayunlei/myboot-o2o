/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月5日
 * Description:NoStandardUpgradeInfoVo.java 
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
public class NoStandardUpgradeInfoVo {

	@ApiModelProperty("批次id")
	private Long bactId;

	@ApiModelProperty("SKUID")
	private Long skuId;

	@ApiModelProperty("商品名称")
	private String productName;

	@ApiModelProperty("空间id")
	private Long roomId;

	@ApiModelProperty("空间名称")
	private String roomName;

	@ApiModelProperty("升级费用")
	private BigDecimal upgradeFee;

	@ApiModelProperty("类目id")
	private Integer categoryId;

	@ApiModelProperty("类目名称")
	private String categoryName;

	@ApiModelProperty("是否必选:0否1是")
	private Integer required;
	@ApiModelProperty("商品描述")
	private String productDesc;

	private String productImage;//商品图地址

}
