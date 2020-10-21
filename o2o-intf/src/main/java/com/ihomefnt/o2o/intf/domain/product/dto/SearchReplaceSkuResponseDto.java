package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("搜索同类SKU返回对象")
public class SearchReplaceSkuResponseDto {

    @ApiModelProperty("搜索同类SKU返回对象")
    private PageDTO<SearchReplaceSkuDto> pageInfo;
}
