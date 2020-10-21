package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 硬装商品明细
 * @author ZHAO
 */
@Data
public class AladdinHardItemVo {
	private String itemDesciption;//描述
	
	private String unit;//计量单位
	
	private Integer itemCount;//数量
	
	private String categoryName;//类别名称
	
	private String productName;//商品名称
	
	private String productImge;//图片
	
	private Integer propertyType;//商品属性 2-增减项 3-硬装升级项 4-非标准升级项 5-增配包
	
	private Integer incrementFlag;//增减标识 0-增 1-减
}
