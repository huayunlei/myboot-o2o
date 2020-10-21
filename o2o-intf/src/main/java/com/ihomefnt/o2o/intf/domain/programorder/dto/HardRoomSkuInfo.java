package com.ihomefnt.o2o.intf.domain.programorder.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author liguolin
 *
 */
@Data
@ApiModel("硬装空间信息")
public class HardRoomSkuInfo extends BaseRoomSkuInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -717749914671093472L;
	
	/**
	 * 空间售卖价
	 */
	@ApiModelProperty("空间售卖价")
	private BigDecimal hardRoomSalePrice;
	
	/**
	 * 空间毛利
	 */
	@ApiModelProperty("空间毛利")
	private Integer hardRoomProfit;
	
	
	/**
	 * 硬装标准描述
	 */
	@ApiModelProperty("硬装标准描述")
	private String hardListDesc;

	private List<SoftRoomSkuInfo.SimpleRoomSkuDto> skuInfos;
}
