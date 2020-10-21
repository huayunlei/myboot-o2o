/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年12月8日
 * Description:ShelveListResponeVo.java 
 */
package com.ihomefnt.o2o.intf.domain.product.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class ShelveListResponeVo {

	/**
	 * 未上架的skuList
	 */
	private List<Integer> offShelveList;

	/**
	 * 已上架的skuList
	 */
	private List<Integer> onShelveList;
}
