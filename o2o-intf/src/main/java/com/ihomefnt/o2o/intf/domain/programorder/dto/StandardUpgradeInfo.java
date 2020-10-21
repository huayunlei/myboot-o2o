/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月4日
 * Description:StandardUpgradeInfo.java
 */
package com.ihomefnt.o2o.intf.domain.programorder.dto;

/**
 * @author zhang
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@ApiModel("标准升级项返回对象")
public class StandardUpgradeInfo implements Serializable {

    @ApiModelProperty("主键标识")
    private Long batchId;

    @ApiModelProperty("sku id")
    private Integer skuId;

    @ApiModelProperty("sku name")
    private String productName;

    @ApiModelProperty("商品图地址")
    private String productImage;//商品图地址

    @ApiModelProperty("类目id")
    private Integer categoryId;

    @ApiModelProperty("类目名称")
    private String categoryName;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("升级费用")
    private BigDecimal upgradeFee;
}
