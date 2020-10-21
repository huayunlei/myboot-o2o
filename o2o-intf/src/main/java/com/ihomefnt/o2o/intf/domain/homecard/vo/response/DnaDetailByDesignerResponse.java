/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年3月2日
 * Description:DnaDetailByDesignerResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class DnaDetailByDesignerResponse {

	/**
	 * DNA ID
	 */
	private Integer dnaId;

	/**
	 * 首图
	 */
	private String headImgUrl;

	/**
	 * 风格
	 */
	private String style;

	/**
	 * DNA名称
	 */
	private String name;

	/**
	 * 文案
	 */
	private String praise;

	/**
	 * 喜欢的人数
	 */
	private Integer favoriteNum;

	/**
	 * 评论数
	 */
	private Integer commentNum;

}
