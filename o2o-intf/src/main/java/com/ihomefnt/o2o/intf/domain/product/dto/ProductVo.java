/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年6月8日
 * Description:ProductVo.java 
 */
package com.ihomefnt.o2o.intf.domain.product.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel(description = "商品")
public class ProductVo {
    
    @ApiModelProperty(value = "主键")
    private Integer id;
    
    @ApiModelProperty(value = "草稿id")
    private String mongoId;

    @ApiModelProperty(value = "skuId")
    private Integer skuId;

    @ApiModelProperty(value = "模板id")
    private Integer templateId;
    
    @ApiModelProperty(value = "品类id")
    private Integer categoryId;
    
    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "品牌id")
    private Integer brandId;

    @ApiModelProperty(value = "风格id")
    private Integer styleId;

    @ApiModelProperty(value = "图文详情")
    private String graphicDetails;

    @ApiModelProperty(value = "spu属性")
    private List<ProductPropertyValuesVo> productSpuPropertyList;

    @ApiModelProperty(value = "sku属性")
    private List<ProductPropertyValuesVo> productSkuPropertyList;

    @ApiModelProperty(value = "sku体")
    private List<SkuVo> skuList;

}

