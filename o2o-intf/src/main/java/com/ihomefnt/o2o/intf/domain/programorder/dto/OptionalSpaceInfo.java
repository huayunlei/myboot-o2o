package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 可替换空间信息
 * Author: ZHAO
 * Date: 2018年5月2日
 */
@Data
public class OptionalSpaceInfo {
	private Integer spaceId;//空间id
	
	private String spaceName;//空间名称
	
	private String spaceUseName;//空间用途名称
	
	private Integer isDefault;//是否默认 0否 1是
	
	private String roomImgUrl;//空间效果图

	private String spaceSeriesName;//空间方案套系
	
	private String spaceStyleName;//空间方案风格
	
	private BigDecimal roomSalePrice;//空间售卖价
	
	private Integer roomFurnitureNum;//家具总件数

	public OptionalSpaceInfo() {
		this.spaceId = -1;
		this.spaceName = "";
		this.spaceUseName = "";
		this.isDefault = 0;
		this.roomImgUrl = "";
		this.spaceSeriesName = "";
		this.spaceStyleName = "";
		this.roomSalePrice = BigDecimal.ZERO;
		this.roomFurnitureNum = 0;
	}


}
