package com.ihomefnt.o2o.intf.domain.sku.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("定制品属性")
public class CustomItemInfoDto{

    @ApiModelProperty("属性名称")
    private String name;

    @ApiModelProperty("属性值")
    private String value;

    @ApiModelProperty("子属性")
    private List<CustomItemDto> attrs;
}
