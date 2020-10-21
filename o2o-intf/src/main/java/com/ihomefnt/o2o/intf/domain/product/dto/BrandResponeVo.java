/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年12月8日
 * Description:BrandResponeVo.java 
 */
package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class BrandResponeVo {

	private Integer skuId;

	/**
	 * 品牌id
	 */
	private Integer brandId;

	/**
	 * 品牌名称
	 */
	private String brandName;

}
