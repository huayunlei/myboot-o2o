package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

/**
 * 软装商品明细
 * @author ZHAO
 */
@Data
public class AladdinSoftItemVo {
	private Integer skuId;//skuId

	private String imageUrl;//图片路径
	
	private String productName;//商品名称
	
	private String brandAndSeries;//品牌&系列名称
	
	private String specifications;//商品规格
	
	private Integer productCount;//商品数量
	
	private Integer furnitureType;//商品类型
	
	private Integer productStatus;//商品状态
	
	private String productStatusName;//商品状态:-1待交付 0待采购1采购中2代送货3送货中4送货完成7以取消

	private String categoryName;//二级类目

	private Integer lastCategoryId;// 末级类目Id

	private String lastCategoryName;// 末级类目名称
}
