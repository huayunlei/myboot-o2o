package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 代客下单软装子订单列表
 * @author ZHAO
 */
@Data
public class AppValetOrderInfoSoftDetailVo {
	private Integer skuId;//skuId
	
	private String productImage;//图片路径
	
	private String productName;//商品名称
	
	private Integer productCount;//商品数量
	
	private Integer productStatus;//商品状态
	
	private String productStatusStr;//商品状态字符串
	
	private String specifications;//商品规格
	
	private String material;//材质
	
	private Double length;//长
	
	private Double width;//宽
	
	private Double height;//高

	private String superKey;// 唯一标识
}
