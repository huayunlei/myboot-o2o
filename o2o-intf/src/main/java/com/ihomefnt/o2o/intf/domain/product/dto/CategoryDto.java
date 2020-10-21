package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("类目")
public class CategoryDto {
    @ApiModelProperty("类目id")
    private Integer categoryId;

    @ApiModelProperty("类目名称")
    private String categoryName;
}
