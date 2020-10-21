package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("属性值")
public class ProductPropertyValueDto {

    @ApiModelProperty("属性值id")
    private Long propertyValueId;

    @ApiModelProperty("属性值")
    private String propertyValue;
}
