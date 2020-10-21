package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel("是否有订单")
public class HasOrderDto implements Serializable {

    @ApiModelProperty("是否有订单 默认fasle")
    private boolean hasOrder = false;
}
