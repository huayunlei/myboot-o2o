/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年11月28日
 * Description:ProductResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class ProductResponseVo {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 商品Id
	 */
	private Integer productId;

	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 分类id
	 */
	private Integer categoryId;

	/**
	 * 分类名称
	 */
	private String categoryName;

	/**
	 * 市场价
	 */
	private BigDecimal marketPrice;
	/**
	 * 艾佳价
	 */
	private BigDecimal aijiaPrice;

	/**
	 * 品牌Id
	 */
	private Integer brandId;

	/**
	 * 品牌名称
	 */
	private String brandName;

	/**
	 * 长
	 */
	private Integer length;

	/**
	 * 宽
	 */
	private Integer width;

	/**
	 * 高
	 */
	private Integer height;

	/**
	 * 商品图片
	 */
	private List<String> images;

}
