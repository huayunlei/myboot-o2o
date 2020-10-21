package com.ihomefnt.o2o.intf.domain.artist.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@ApiModel("小星星订单商品明细")
public class StarOrderDetailCreateRequest {

	/**
     * 商品id
     */
	@ApiModelProperty("商品id")
    private Integer productId;
    /**
     * 商品数量
     */
	@ApiModelProperty("商品数量")
    private Integer productCount;
    /**
     * 商品售价
     */
	@ApiModelProperty("商品售价")
    private BigDecimal productPrice;
    /**
     * 商品备注
     */
	@ApiModelProperty("商品备注")
    private String remark;
	
	/**
     * 艺术品id
     */
	@ApiModelProperty("艺术品id")
    private Integer artId;
	/**
     * 艺术品名称
     */
	@ApiModelProperty("艺术品名称")
    private String artName;
	
	/**
     * 用户选择属性
     */
	@ApiModelProperty("用户选择属性")
    private String selectAttr;
	/**
     * 商品配文
     */
	@ApiModelProperty("商品配文")
    private String markWord;
}
