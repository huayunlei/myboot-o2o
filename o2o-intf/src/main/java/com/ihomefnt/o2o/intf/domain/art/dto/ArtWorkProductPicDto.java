package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanyunxin
 * @create 2019-08-08 11:36
 */
@NoArgsConstructor
@Data
@ApiModel(value = "产品图片集合")
public class ArtWorkProductPicDto {

    @ApiModelProperty("产品id")
    private String productId;

    @ApiModelProperty("产品图片")
    private String productPicUrl;
}
