/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月16日
 * Description:ImageEntity.java 
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhang
 */
@Data
public class ImageEntity implements Serializable{

	/**
	 * 小图
	 */
	private String smallImage;

	/**
	 * 大图
	 */
	private String bigImage;
}
