/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: 闫辛未
 * Date: 2018/8/31
 * Description:SearchReplaceSkuParam.java
 */
package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("搜索同类SKU返回")
public class SearchReplaceSkuDto {

        @ApiModelProperty("skuId")
        private Long skuId;

        @ApiModelProperty("productId")
        private Long productId;

        @ApiModelProperty("商品名称")
        private String productName;

        @ApiModelProperty("商品名称")
        private String furnitureName;

        @ApiModelProperty("品牌名称")
        private String brandName;

        @ApiModelProperty("品牌名称(前台兼容老数据)")
        private String brand;

        @ApiModelProperty("颜色")
        private String color;

        @ApiModelProperty("材质")
        private String material;

        @ApiModelProperty("长宽高尺寸")
        private String itemSize;

        @ApiModelProperty("长 mm")
        private Integer length;

        @ApiModelProperty("宽 mm")
        private Integer width;

        @ApiModelProperty("高 mm")
        private Integer height;

        @ApiModelProperty("类型")
        private Integer productType;

        @ApiModelProperty("sku头图")
        private String skuImg;

        @ApiModelProperty("SKU头图-切图")
        private String smallImage;

        @ApiModelProperty("售卖单价")
        private BigDecimal price;

        @ApiModelProperty("差价")
        private BigDecimal priceDiff;

        @ApiModelProperty("末级类目id")
        private Integer lastCategoryId;

        @ApiModelProperty("末级类目名称")
        private String lastCategoryName;

        @ApiModelProperty("风格id")
        private Integer styleId;

        @ApiModelProperty("风格名称")
        private String styleName;

        @ApiModelProperty("规格")
        private String pvs;

        @ApiModelProperty("家具类型")
        private Integer furnitureType;

        @ApiModelProperty("系列id")
        private Integer seriesId;

        @ApiModelProperty("系列名称")
        private String seriesName;

        @ApiModelProperty("建议适配床垫最小高")
        private Integer suggestMattressMinHeight;

        @ApiModelProperty("建议适配床垫长")
        private Integer suggestMattressLength;

        @ApiModelProperty("建议适配床垫宽度")
        private Integer suggestMattressWidth;

        @ApiModelProperty("建议适配床垫最大高")
        private Integer suggestMattressMaxHeight;

        @ApiModelProperty("异常状态 0:正常,1:床垫尺寸和床不匹配")
        private Integer errorStatus = 0;

        @ApiModelProperty("1 是免费赠品 0 非免费赠品")
        private Integer freeFlag = 0;

        @ApiModelProperty(" 0 不展示 1可替换为赠品 2免费赠品 4效果图推荐")//免费赠品优先级高于效果图推荐
        private Integer showFreeFlag = 0;
}
