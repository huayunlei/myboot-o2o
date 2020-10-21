/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月2日
 * Description:ImageAspectRatioEntity.java 
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhang
 */
@Data
public class ImageAspectRatioEntity implements Serializable {

	private String spaceImageUrl;

	private BigDecimal aspectRatio; // 宽高比

}
