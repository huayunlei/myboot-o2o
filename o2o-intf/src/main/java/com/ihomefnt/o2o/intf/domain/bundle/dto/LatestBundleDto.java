/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月15日
 * Description:BundleResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.bundle.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class LatestBundleDto {

	// 下载路径
	private String url;

	// 最终bundle版本号
	private String lastVersion;
}
