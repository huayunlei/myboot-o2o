package com.ihomefnt.o2o.intf.domain.programorder.dto;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "软装订单详情")
public class OrderSoftDetailResultDto {
    
    @ApiModelProperty(value = "软装订单状态")
    private Integer softOrderStatus;
    
    @ApiModelProperty(value = "软装订单状态名称")
    private String softOrderStatusStr;

    @ApiModelProperty(value = "软装空间信息")
    private List<SoftRoomSkuInfo> softRoomInfos = new ArrayList<SoftRoomSkuInfo>();

    @ApiModelProperty(value = "软装无空间信息")
    private List<SoftRoomSkuInfo.SimpleRoomSkuDto> softNoRoomList;

    @ApiModelProperty("商品总数")
    private int totalProductCount;

    @ApiModelProperty("商品总价")
    private BigDecimal totalProAmount;
}
