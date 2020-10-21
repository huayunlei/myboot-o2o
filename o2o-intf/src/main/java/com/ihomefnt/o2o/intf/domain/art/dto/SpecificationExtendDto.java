package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-09 13:49
 */
@Data
@ApiModel("艺术品属性，商品中心返回数据")
public class SpecificationExtendDto {


    @ApiModelProperty("图片")
    private String picUrl;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("规格ID")
    private String specificationId;

    @ApiModelProperty("供应商")
    private String supplierName;

    @ApiModelProperty("扩展属性")
    private List<PropertyDto> extendList;

}
