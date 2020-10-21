package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wanyunxin
 * @create 2019-08-09 14:42
 */
@ApiModel("艺术品商品对象")
@Data
public class ArtSpecificationDto {

    @ApiModelProperty("规格ID")
    private String specificationId;

    @ApiModelProperty("艺术品id")
    private String worksId;

    @ApiModelProperty("图片")
    private String picUrl;

    @ApiModelProperty("价格")
    private BigDecimal price;
}
