package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 可替换SKU信息
 *
 * @author ZHAO
 */
@Data
public class SolutionRoomSubItemVo implements Serializable {
    // 风格id  风格名称 三级类目id 三级类目名称 四级类目id 四级类目名称 二级类目id 二级类目名称
    /**
     * 二级类目id
     */
    private Integer categoryLevelTwoId;

    /**
     * 二级类目名称 和 typeTwoName 一样，兼容老接口
     */
    private String categoryLevelTwoName;

    /**
     * 三级类目id
     */
    private Integer categoryLevelThreeId;

    /**
     * 三级类目名称
     */
    private String categoryLevelThreeName;

    /**
     * 四级类目Id
     */
    private Integer categoryLevelFourId;

    /**
     * 四级类目名称
     */
    private String categoryLevelFourName;

    /**
     * 末级类目Id
     */
    private Integer lastCategoryId;

    /**
     * 末级类目名称
     */
    private String lastCategoryName;

    /**
     * 风格Id
     */
    private Integer styleId;

    /**
     * 风格名称
     */
    private String styleName;

    // 新增的8个字段

    private Integer sequenceNum;//商品排序序号

    private Integer furnitureType;//0成品家具  1定制家具  2赠品家具 3新定制品 4bom

    private Integer skuId;//商品skuId

    private String itemName;//DNA空间商品名称

    private String itemTopBrand;//商品一级品牌名称

    private String itemBrand;//DNA空间商品品牌

    private String itemColor;//DNA空间商品颜色

    private String itemMaterial;//DNA空间商品材质描述

    private String material;//DNA空间商品材质

    private String itemSize;//DNA空间商品尺寸

    private Integer itemCount;//DNA空间商品件数

    private Integer length;//SKU长/窗帘的长

    private Integer seriesId;//系列id

    private String seriesName;//系列

    private Integer width;//SKU宽

    private Integer height;//SKU高

    private BigDecimal priceDiff;//差价

    private BigDecimal skuPrice;//SKU价格

    private BigDecimal price;//软装*数量后的价格

    private String itemImage;//SKU图片

    private String typeTwoName;//二级类目名称

    private Integer parentSkuId;//

    private Integer skuVisibleFlag;//是否支持可视化 1支持 0不支持

    private Integer productType;//商品类型

    private boolean appCustomizable;//app 是否可定制

    private String ruleSize;//规格

    @ApiModelProperty("是否可选免费赠品 1 可选 0不可选")
    private Integer freeAble = 0;

    @ApiModelProperty("1 是免费赠品 0 非免费赠品")
    private Integer freeFlag = 0;

}
