/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月11日
 * Description:OrderDetailDtoVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
public class OrderDetailDtoVo {

	/**
	 * 订单明细id
	 */
	private Integer idtDetail;

	/**
	 * 订单id
	 */
	private Integer fidOrder;

	/**
	 * 套装id
	 */
	private Integer fidSuit;

	/**
	 * 空间id
	 */
	private Integer fidSuitRoom;

	/**
	 * 商品id
	 */
	private Integer fidProduct;

	@ApiModelProperty("新版艾商城skuid")
	private String skuId;

	@ApiModelProperty("作品或商品 为0 取fidProduct 其他取skuId")
	private Integer skuType;

	/**
	 * 商品数量
	 */
	private Integer productAmount;

	/**
	 * 商品单价
	 */
	private BigDecimal productPrice;

	/**
	 * 商品总价
	 */
	private BigDecimal productAmountPrice;

	/**
	 * 厂家编号
	 */
	private String factoryNoVersion;

	/**
	 * 订单的商品状态
	 */
	private Integer state;

	/**
	 * 是否是样品
	 */
	private Integer sample;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 1、商品 2货物
	 */
	private Integer type;

	/**
	 * 快递编号
	 */
	private String deliveryNumber;

	/**
	 * 快递公司名称
	 */
	private String deliveryName;

	/**
	 * 快递公司编码
	 */
	private String deliveryCode;
	
	private Integer deteleFlag;//删除标志位
	
	/**
	 * 小星星艺术品id
	 */
	private Integer fidStarArt;
	
	/**
     * 评价内容
     */
    private String comment;
    
    /**
     * 备注
     */
    private String remarks;

	@ApiModelProperty("定制内容")
	private String customizedContent;

	private String selectAttr;   //用户选择属性

	private String propertyNameValue;
    

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}


}
