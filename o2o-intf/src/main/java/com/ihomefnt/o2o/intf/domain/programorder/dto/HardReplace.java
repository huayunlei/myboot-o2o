package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2018/7/24
 */
@ApiModel("硬装替换商品")
@Data
@Accessors(chain = true)
public class HardReplace {

    @ApiModelProperty("硬装项目分类")
    private Integer roomClassId;

    @ApiModelProperty("原sku")
    private Integer skuId;

    @ApiModelProperty("原工艺")
    private Integer craftId;

    @ApiModelProperty("新sku")
    private Integer newSkuId;

    @ApiModelProperty("新工艺")
    private Integer newCraftId;

    @ApiModelProperty("父商品工艺Id")
    private Integer parentCraftId;

    @ApiModelProperty("父商品SkuId")
    private Integer parentSkuId;

}
