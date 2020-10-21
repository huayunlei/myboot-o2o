package com.ihomefnt.o2o.intf.domain.house.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class THouse {
	private Long houseId;
	private String houseName;
	private BigDecimal area;
	private String pictureUrlOriginal;
	private Byte chamber;//卧室
	private Byte hall;//厅
	private Byte toilet;//卫
	private Byte kitchen;//厨房
	private Byte balcony;//阳台
	private Byte hasHidden;//户型是否隐藏 0未隐藏，1已隐藏
	
}
