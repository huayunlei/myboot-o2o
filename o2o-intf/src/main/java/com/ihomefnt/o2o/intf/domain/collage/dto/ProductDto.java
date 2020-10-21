package com.ihomefnt.o2o.intf.domain.collage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jerfan cang
 * @date 2018/10/16 11:26
 */
@Data
@ApiModel("ProductDto")
public class ProductDto {

     @ApiModelProperty("活动ID")
     private Integer activityId;

    @ApiModelProperty("商品id")
     private Integer productId;

    @ApiModelProperty("商品名称")
     private String productName;

    @ApiModelProperty("商品头图")
     private String headImage;

    @ApiModelProperty("商品品牌图")
    private String brandIcon;

    @ApiModelProperty("商品描述")
     private String description;

    @ApiModelProperty("原价")
     private BigDecimal originPrice;

    @ApiModelProperty("活动价")
     private BigDecimal price;

    @ApiModelProperty("商品标签-原始的")
    private String productTags;
}
