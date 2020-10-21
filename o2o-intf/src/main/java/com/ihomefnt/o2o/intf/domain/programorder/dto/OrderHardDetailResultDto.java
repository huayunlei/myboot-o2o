package com.ihomefnt.o2o.intf.domain.programorder.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Data
@ApiModel(description = "硬装订单详情")
public class OrderHardDetailResultDto {
    
    @ApiModelProperty(value = "硬装订单状态")
    private Integer hardOrderStatus;
    
    @ApiModelProperty(value = "硬装订单状态名称")
    private String hardOrderStatusStr;
    
    @ApiModelProperty(value = "硬装空间信息")
    private List<HardRoomSkuInfo> hardRoomInfos = new ArrayList<HardRoomSkuInfo>();

//    @ApiModelProperty(value = "硬装进展信息")
//    private ProjectDetailProgressInfoVo projectDetailProgressInfoVo;

    @ApiModelProperty(value = "硬装面积补差价")
    private BigDecimal hardExtraSalePrice;
}
