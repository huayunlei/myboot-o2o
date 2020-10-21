/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: 闫辛未
 * Date: 2018/7/24
 * Description:HardItemCraft.java
 */
package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.RoomHardItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 闫辛未
 */
@ApiModel("空间硬装sku工艺")
@Data
public class HardItemCraft implements Serializable{
    @ApiModelProperty("工艺id")
    private Integer craftId;

    @ApiModelProperty("工艺名称")
    private String craftName;

    @ApiModelProperty("使用这种工艺的价格")
    private BigDecimal totalPrice;

    @ApiModelProperty("差价")
    private BigDecimal priceDiff;

    @ApiModelProperty("工艺示意图")
    private String craftImage;

    @ApiModelProperty("是否默认工艺")
    private boolean craftDefault;

    @ApiModelProperty("硬装sku列表")
    private List<RoomHardItem> roomHardItemList;
}
