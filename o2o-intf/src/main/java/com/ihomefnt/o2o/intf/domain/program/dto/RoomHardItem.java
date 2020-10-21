/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: 闫辛未
 * Date: 2018/7/24
 * Description:RoomHardItem.java
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.programorder.dto.HardItemCraft;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 闫辛未
 */
@Data
@ApiModel("空间硬装sku")
public class RoomHardItem implements Serializable{
    @ApiModelProperty("硬装skuID")
    private Integer hardItemId;

    @ApiModelProperty("选配包名称")
    private String hardItemName;

    @ApiModelProperty("选配包别名")
    private String hardItemShortName;

    @ApiModelProperty("头图")
    private String hardItemHeadImage;

    @ApiModelProperty("选配包描述")
    private String hardItemDesc;

    @ApiModelProperty("可选工艺列表")
    private List<HardItemCraft> hardItemCraftList;

    @ApiModelProperty("末级类目id")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;
}
