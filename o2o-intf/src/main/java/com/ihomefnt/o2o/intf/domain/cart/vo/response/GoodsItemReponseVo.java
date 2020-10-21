/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: Ivan Shen
 * Date: 2017/1/11
 * Description:GoodsVo.java
 */
package com.ihomefnt.o2o.intf.domain.cart.vo.response;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 购物车商品
 *
 * @author Ivan Shen
 */
@ApiModel("购物车商品")
@Data
public class GoodsItemReponseVo {
    
    @ApiModelProperty("购物商品记录ID")
    private Integer recordId;
    
    @ApiModelProperty("商品ID (sku_type=0时存在)")
    private Integer goodsId;

    @ApiModelProperty("商品类型 0:默认 1:定制商品 2:作品 ")
    private Integer skuType;

    @ApiModelProperty("商品ID (sku_type=1 或2时存在)")
    private String skuId;

    @ApiModelProperty("是否支持特色定制 0 非 1是")
    private Integer specialFlag = 0;

    @ApiModelProperty("商品类型(艺术品:5)")
    private Integer goodsType;
    
    @ApiModelProperty("商品名称")
    private String goodsName;
    
    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;
    
    @ApiModelProperty("商品图片")
    private String goodsImage;

    @ApiModelProperty("商品描述(拼接品牌、尺寸等)")
    private String goodsDescription;

    @ApiModelProperty("商品数量")
    private Integer goodsAmount;
    
    @ApiModelProperty("商品库存")
    private Integer goodsStock;
    
    @ApiModelProperty("商品状态：1.上线 0.下架")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;
    
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "艺术品id")
    private String worksId;

    @ApiModelProperty(value = "产品ID")
    private String productId;

}
