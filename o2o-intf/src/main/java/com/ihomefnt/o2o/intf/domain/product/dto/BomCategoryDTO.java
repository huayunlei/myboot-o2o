package com.ihomefnt.o2o.intf.domain.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * APP组合分类信息DTO
 * </p>
 *
 * @author lushuo
 * @since 2019-02-14
 */
@Data
@ApiModel(value = "APP组合分类信息DTO")
public class BomCategoryDTO {

    @ApiModelProperty("组合id")
    private Integer groupId;

    @ApiModelProperty("组合分类id")
    private Integer lastCategoryId;

    @ApiModelProperty("组合分类名称")
    private String lastCategoryName;

}
