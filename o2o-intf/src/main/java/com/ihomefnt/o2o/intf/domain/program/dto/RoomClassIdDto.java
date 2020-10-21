package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 临时用
 * @author xiamingyu
 * @date 2019/1/19
 */
@Data
@ApiModel("硬装skuId和分类id查询返回")
public class RoomClassIdDto {

    @ApiModelProperty("硬装项目分类")
    private Integer roomClassId;

    @ApiModelProperty("原sku")
    private Integer skuId;
}
