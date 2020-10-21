/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年3月6日
 * Description:DnaInfoResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class DnaInfoResponse {

	/**
	 * DNAID
	 */
	private Integer dnaId;

	/**
	 * DNA名称
	 */
	private String dnaName;

	/**
	 * DNA首图
	 */
	private String dnaHeadImage;

	/**
	 * 系列名
	 */
	private String seriesName;

	/**
	 * 风格名
	 */
	private String styleName;

	/**
	 * 标签列表
	 */
	private String dnaTags;

	/**
	 * 短文案,较短
	 */
	private String shortDesc;

	/**
	 * 计理念,较长
	 */
	private String designIdea;
}
