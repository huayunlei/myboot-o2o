package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 全品家订单增配包信息
 * @author ZHAO
 */
@Data
public class AladdinAddBagInfoVo {
	private Integer skuId;//商品id
	
	private String productName;//商品名称
	
	private String productUrl;//商品首图地址
	
	private Integer productCount;//商品数量
	
	private BigDecimal productPrice;//商品单价
	
	private String categoryName;//类目名称
	
	private Integer type;//类型5：硬装 6：软装
	
	private String specifications;//商品规格
	
	private String brandAndSeries;//品牌&系列名称
	
	private Integer furnitureType;//商品类型
	
}
