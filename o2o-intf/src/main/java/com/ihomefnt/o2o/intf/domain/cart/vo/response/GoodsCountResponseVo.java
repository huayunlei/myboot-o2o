/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: Ivan Shen
 * Date: 2017/1/11
 * Description:GoodsCountResultVo.java
 */
package com.ihomefnt.o2o.intf.domain.cart.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 购物车商品总数
 *
 * @author Ivan Shen
 */
@Data
@ApiModel("购物车商品总数")
public class GoodsCountResponseVo {

    @ApiModelProperty("商品总数")
    private Integer count;
}
