/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: 闫辛未
 * Date: 2018/7/25
 * Description:RoomDefaultHardItem.java
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 闫辛未
 */
@Data
public class RoomDefaultHardItemClass implements Serializable {
    @ApiModelProperty("选配类别id")
    private Integer hardItemClassId;

    @ApiModelProperty("选配类别名称")
    private String hardItemClassName;

    @ApiModelProperty("选配类别示意图")
    private String hardItemClassImage;

    @ApiModelProperty("选配项目描述")
    private String hardItemClassDesc;

    @ApiModelProperty("硬装skuID")
    private Integer hardItemId;

    @ApiModelProperty("sku名称")
    private String hardItemName;

    @ApiModelProperty("sku头图")
    private String hardItemHeadImage;

    @ApiModelProperty("sku描述")
    private String hardItemDesc;

    @ApiModelProperty("工艺id")
    private Integer craftId;

    @ApiModelProperty("工艺名称")
    private String craftName;

    @ApiModelProperty("使用这种工艺的价格")
    private BigDecimal totalPrice;

    @ApiModelProperty("工艺示意图")
    private String craftImage;

    @ApiModelProperty(value = "品牌id")
    private Integer brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;
}
