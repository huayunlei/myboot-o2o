/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年6月21日
 * Description:AreaInfoDto.java 
 */
package com.ihomefnt.o2o.intf.domain.address.dto;

import lombok.Data;

/**
 * 区域地址信息
 * 
 * @author chong
 */
@Data
public class AreaInfoDto {

	// 区域id
	private Long areaId;

	// 区域名称
	private String areaName;

	// 市区id
	private Long cityId;

	// 市区名称
	private String cityName;

	// 省id
	private Long provinceId;

	// 省名称
	private String provinceName;


}
