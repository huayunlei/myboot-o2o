/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: Ivan Shen
 * Date: 2017/1/11
 * Description:GoodsListResultVo.java
 */
package com.ihomefnt.o2o.intf.domain.cart.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 购物车商品列表返回
 *
 * @author Ivan Shen
 */
@Data
@ApiModel("购物车商品列表返回")
public class GoodsListResponseVo {
    
    @ApiModelProperty("有效商品列表")
    private List<GoodsItemReponseVo> enableGoodsList;
    
    @ApiModelProperty("无效商品列表")
    private List<GoodsItemReponseVo> disableGoodsList;
}
