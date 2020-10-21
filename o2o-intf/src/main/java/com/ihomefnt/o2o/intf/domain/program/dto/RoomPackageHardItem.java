package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jerfan cang
 * @date 2018/9/30 14:59
 */
@Data
public class RoomPackageHardItem {

    @ApiModelProperty("硬装skuID")
    private Integer hardItemId;

    @ApiModelProperty("sku名称")
    private String hardItemName;

    @ApiModelProperty("商品简称")
    private String hardItemShortName;

    @ApiModelProperty("sku头图")
    private String hardItemHeadImage;

    @ApiModelProperty("sku描述")
    private String hardItemDesc;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "规格")
    private String pvs;

    @ApiModelProperty(value = "材质")
    private String material;

    @ApiModelProperty(value = "单位Str")
    private String unitStr;

    @ApiModelProperty(value = "用量")
    private BigDecimal quantities = BigDecimal.ZERO;

}
