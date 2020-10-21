/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年3月6日
 * Description:DesignDealNumsResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class DesignDealNumsResponseVo {

	/**
	 * 设计师用户id
	 */
	private Integer userId;

	/**
	 * dna空间成交的总数量
	 */
	private Integer dealNums;
}
