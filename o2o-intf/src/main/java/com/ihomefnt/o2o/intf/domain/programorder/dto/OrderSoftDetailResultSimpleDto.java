package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "软装简单信息")
@Data
public class OrderSoftDetailResultSimpleDto {

    @ApiModelProperty(value = "软装空间信息")
    private List<SoftRoomSkuSimpleInfo> softRoomInfos = new ArrayList<SoftRoomSkuSimpleInfo>();

    @ApiModelProperty(value = "软装无空间信息")
    private List<SoftSkuSimpleInfo> softNoRoomList;


}
