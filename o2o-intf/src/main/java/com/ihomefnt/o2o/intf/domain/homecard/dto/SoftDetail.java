package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * 软装清单
 * @author ZHAO
 */
@Data
public class SoftDetail implements Serializable{
	private Integer skuId = 0;//DNA空间商品skuId
	
	private String name = "";//名称
	
	private Integer itemCount = 0;//空间商品件数
	
	private String brand = "";//品牌
	
	private String color = "";//颜色
	
	private String material = "";//材质
	
	private String matterSize = "";//物品尺寸
}
