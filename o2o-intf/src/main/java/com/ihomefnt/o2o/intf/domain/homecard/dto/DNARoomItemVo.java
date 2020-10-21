package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

/**
 * 空间家具
 * @author ZHAO
 */
@Data
public class DNARoomItemVo {
	private Integer skuId;//DNA空间商品skuId
	
	private String itemName;//DNA空间商品名称
	
	private Integer itemCount;//DNA空间商品件数
	
	private String itemBrand;//DNA空间商品品牌
	
	private String itemColor;//DNA空间商品颜色
	
	private String itemMaterial;//DNA空间商品材质
	
	private String itemSize;//DNA空间商品尺寸

	private Integer furnitureType;//0成品家具  1定制家具  2赠品家具 3新定制品 4bom
}
