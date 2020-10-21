/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年3月2日
 * Description:DesignerMoreDetailResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class DesignerMoreDetailResponse {

	/**
	 * 设计师id
	 */
	private Integer designerId;

	/**
	 * 设计师头像
	 */
	private String headImgUrl;

	/**
	 * 设计师背图
	 */
	private String desingerBackImage;

	/**
	 * 设计师名称
	 */
	private String designerName;

	/**
	 * 设计师标签
	 */
	private String designerLables;

	/**
	 * 设计师描述
	 */
	private List<String> designerDescs;

	/**
	 * dna作品数量
	 */
	private int dnaCountSize;

	/**
	 * 成交量:每卖出一套此设计师设计的空间，则成交数＋1
	 */
	private int tradeCountSize;

	/**
	 * dna列表
	 */
	private List<DnaDetailByDesignerResponse> dnaList;
}
