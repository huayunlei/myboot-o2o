/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年11月28日
 * Description:ProductProxyEntity.java 
 */
package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class ProductListResponseVo {

	/**
	 * 总数量
	 */
	private Integer totalCount;

	/**
	 * 当前页
	 */
	private Integer pageNo;

	/**
	 * 每页大小
	 */
	private Integer pageSize;

	/**
	 * 总页数
	 */
	private Integer totalPage;

	/**
	 * 当前页的商品信息
	 */
	private List<ProductResponseVo> list;

}
