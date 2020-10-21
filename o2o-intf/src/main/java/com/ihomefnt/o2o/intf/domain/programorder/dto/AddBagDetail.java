package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@ApiModel("增配包信息")
public class AddBagDetail implements Serializable {

    @ApiModelProperty("类目名称")
    private String categoryName;

    @ApiModelProperty("商品id")
    private Integer skuId;// 商品id

    @ApiModelProperty("商品名称")
    private String skuName;// 商品名称

    @ApiModelProperty("商品首图地址")
    private String skuImgUrl;// 商品首图地址

    @ApiModelProperty("商品数量")
    private Integer skuCount;// 商品数量

    @ApiModelProperty("商品单价")
    private BigDecimal skuUnitPrice;// 商品单价

    @ApiModelProperty("商品总价")
    private BigDecimal skuTotalPrice;// 商品总价

    @ApiModelProperty("商品描述")
    private String skuDesc;//商品描述

    public AddBagDetail() {
        this.skuId = -1;
        this.skuName = "";
        this.skuImgUrl = "";
        this.skuCount = 0;
        this.skuUnitPrice = new BigDecimal(0);
        this.skuTotalPrice = new BigDecimal(0);
        this.skuDesc = "";
    }

}
