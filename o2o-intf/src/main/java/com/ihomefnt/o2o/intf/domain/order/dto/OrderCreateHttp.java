/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月12日
 * Description:OrderCreateHttp.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class OrderCreateHttp {

	private Integer orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;

	/**
	 * 订单编号
	 */
	private String orderNum;

	/**
	 * 顾客姓名
	 */

	private String customerName;

	/**
	 * 顾客电话
	 */
	private String customerTel;

	/**
	 * 订单总价
	 */
	private BigDecimal totalPrice;

	/**
	 * 实际支付金额
	 */
	private BigDecimal actualPayMent;

	/**
	 * 商品总数
	 */
	private Integer productCount;

	/**
	 * 预计收货时间
	 */
	private Date expectedReceiptTime;

	/**
	 * 注册用户id
	 */
	private Integer fidUser;

	/**
	 * 客户收货地址
	 */
	private Integer fidArea;

	/**
	 * 收货人姓名
	 */
	private String receiverName;

	/**
	 * 收货人电话
	 */
	private String receiverTel;

	/**
	 * 静态客户收货地址
	 */
	private String customerAddress;

	/**
	 * 所属公司
	 */
	private Integer fidCompany;

	/**
	 * 订单来源
	 */
	private Integer source;

	/**
	 * 下单时间
	 */
	private Date orderTime;

	/**
	 * 销售id
	 */
	private Integer fidSale;

	/**
	 * 所属项目
	 */
	private Integer fidProject;

	/**
	 * 订单商品清单
	 */
	private List<OrderDetailHttp> detailList;

	/**
	 * 订单备注
	 */
	private String remark;
}
