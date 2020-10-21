package com.ihomefnt.o2o.intf.domain.sku.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("定制品详情")
public class CustomItemDto{

    @ApiModelProperty("定制品名称")
    private String title;

    @ApiModelProperty("定制品属性")
    private List<CustomItemInfoDto> info;
}

