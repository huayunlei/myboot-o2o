package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("品牌")
public class BrandDto {
    @ApiModelProperty("类目id")
    private Integer brandId;

    @ApiModelProperty("类目名称")
    private String brandName;
}
