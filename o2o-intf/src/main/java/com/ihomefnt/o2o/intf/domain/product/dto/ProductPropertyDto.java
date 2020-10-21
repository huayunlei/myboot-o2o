package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("商品属性")
public class ProductPropertyDto {
    @ApiModelProperty("属性id")
    private Long propertyId;

    @ApiModelProperty("属性名")
    private String propertyName;

    @ApiModelProperty("属性值集合")
    private List<ProductPropertyValueDto> propertyValueList;
}
