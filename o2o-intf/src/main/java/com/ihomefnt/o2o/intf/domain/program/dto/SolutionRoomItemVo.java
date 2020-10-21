package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *  空间家具
 * @author ZHAO
 */
@Data
public class SolutionRoomItemVo implements Serializable {
	private Integer sequenceNum;//商品排序序号
	
	private Integer skuId;//商品skuId
	
	private String itemName;//DNA空间商品名称
	
	private String itemBrand;//DNA空间商品品牌
	
	private String itemColor;//DNA空间商品颜色
	
	private String itemMaterial;//DNA空间商品材质
	
	private String itemSize;//DNA空间商品尺寸
	
	private Integer itemCount;//DNA空间商品件数
	
	private Integer furnitureType;//家具类型  0成品家具  1定制家具  2赠品家具 3新定制品 4bom
	
	private String itemTopBrand;//一级品牌名称
	
	private BigDecimal skuPrice;//SKU价格
	
	private String itemImage;//SKU图片
	
	private String typeTwoName;//二级类目名称

	private Integer lastCategoryId;// 末级类目ID

	private String lastCategoryName;// 末级类目名称

	private List<SolutionRoomSubItemVo> subItemVos;//可替换SKU信息

	private Integer parentSkuId;//

	private String productImage;//SKU图片

	@ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
	private Integer freeAble = 0;

	@ApiModelProperty("1 是免费赠品 0 非免费赠品")
	private Integer freeFlag = 0;

}
