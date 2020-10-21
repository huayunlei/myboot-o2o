/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月11日
 * Description:RefundRecordDtoVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhang
 */
@Data
public class RefundRecordDtoVo {

	private Integer id;

	/**
	 * 订单ID
	 */
	private Integer orderId;

	/**
	 * 退款人ID
	 */
	private Integer refundPersonId;

	/**
	 * 退款人姓名
	 */
	private String refundPersonName;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 退款金额
	 */
	private BigDecimal money;

	/**
	 * 实退金额
	 */
	private BigDecimal refundMoney;

	/**
	 * 支付方式
	 */
	private Integer paymentMode;

	/**
	 * 支付号码（pos、银行卡号、支付宝账号、微信号等）单据号
	 */
	private String paymentNo;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 订单编号
	 */
	private String orderNum;

	/**
	 * ajb订单编号
	 */
	private String ajbOrderNum;

	/**
	 * 1.有效 0 无效
	 */
	private Integer status;

	/**
	 * 操作人
	 */
	private Integer operatorId;

	/**
	 * 操作人姓名
	 */
	private String operatorName;

	/**
	 * 审核人
	 */
	private Integer auditorId;

	/**
	 * 审核人姓名
	 */
	private String auditorName;

	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
