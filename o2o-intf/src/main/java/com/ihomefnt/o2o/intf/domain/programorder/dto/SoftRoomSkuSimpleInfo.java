package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "空间软装简单信息")
@Data
public class SoftRoomSkuSimpleInfo {
    @ApiModelProperty("空间id")
    private Integer roomId;

    @ApiModelProperty("空间名称")
    private String roomName;

    @ApiModelProperty("空间图片")
    private String roomImage;

    @ApiModelProperty("空间设计用途id")
    private Integer spaceUsageId;

    @ApiModelProperty("空间下sku信息")
    private List<SoftSkuSimpleInfo> skuInfos;

    @ApiModelProperty("空间下bom信息")
    private List<SoftRoomBomInfo> bomInfos;
}
