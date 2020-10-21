package com.ihomefnt.o2o.intf.domain.programorder.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author liguolin
 *
 */
@ApiModel("空间软装信息")
@Data
public class SoftRoomSkuInfo extends BaseRoomSkuInfo {

	private static final long serialVersionUID = 3112193296360842461L;

	@ApiModelProperty("空间下sku信息")
	private List<SimpleRoomSkuDto> skuInfos;

	@ApiModelProperty("空间下bom信息")
	private List<SoftRoomBomInfo> bomGroupInfoDtos;

	@ApiModelProperty("空间设计用途id")
    private Integer spaceUsageId;

	@ApiModelProperty("空间图片")
	private String roomImage;

	@ApiModel("空间软装sku信息")
	@Data
	public static class SimpleRoomSkuDto implements Serializable {

		private static final long serialVersionUID = -4077083509687964754L;

		@ApiModelProperty("skuId")
		private Integer skuId;

		@ApiModelProperty("商品名称")
		private String productName;

		@ApiModelProperty("商品个数")
		private Integer productCount;

		@ApiModelProperty("尺寸")
		private String productSize;

		@ApiModelProperty("商品状态")
		private Integer productStatus;

		@ApiModelProperty("商品状态")
		private String productStatusStr;

		@ApiModelProperty("（新）商品状态")
		private Integer newStatus;

		@ApiModelProperty("（新）商品状态名称")
		private String newStatusName;

		@ApiModelProperty("商品图片")
		private String productImage;

		@ApiModelProperty("品牌系列")
		private String brandAndSeries;

		@ApiModelProperty("商品规格")
		private String specifications;

		@ApiModelProperty("备注")
		private String remark;

		@ApiModelProperty("SKU 长")
		private Double length;

		@ApiModelProperty("SKU 宽")
		private Double width;

		@ApiModelProperty("SKU 高")
		private Double height;

		@ApiModelProperty("商品标志 1-定制窗帘")
		private Integer mark;

		@ApiModelProperty("家具类型 2-赠品 4-bom组合")
		private Integer furnitureType;

		@ApiModelProperty("赠品标志 非0时，为赠品")
		private Integer giftFlag;

		@ApiModelProperty("家具类型")
		private String furnitureTypeStr;

		@ApiModelProperty("订单类型")
		private Integer orderType;

		@ApiModelProperty("spuId")
		private Integer spuId;

		@ApiModelProperty("分类（硬装）")
		private Integer roomClassId;

		@ApiModelProperty("分类名称")
		private String roomClassName;

		@ApiModelProperty("工艺ID（硬装）")
		private Integer craftId;

		@ApiModelProperty("工艺名称")
		private String craftName;

		@ApiModelProperty("空间用途")
		private Integer roomUseId;

		@ApiModelProperty("空间用途")
		private String roomUseName;

		@ApiModelProperty("空间id")
		private Integer roomId;

		@ApiModelProperty("类目id")
		private Integer productCategory;
		
		@ApiModelProperty("类目名称")
		private String categoryName;

		@ApiModelProperty("末级类目id")
		private Integer lastCategoryId;

		@ApiModelProperty("末级类目名称")
		private String lastCategoryName;

	}

}
