package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;

/**
 * 楼盘户型
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Data
public class BuildingLayoutInfo {
	private Integer houseTypeId = 0;//户型ID
	
	private String houseTypeName = "";//户型名称
	
	private String size = "";//面积
	
	private String layout = "";//格局
	
	private String imgUrl = "";//户型图
}
