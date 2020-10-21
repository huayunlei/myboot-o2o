package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-07 10:14
 */
@ApiModel("价格区间")
@Data
@AllArgsConstructor
public class PriceSectionDto {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("价格区间描述")
    private String description;
}
