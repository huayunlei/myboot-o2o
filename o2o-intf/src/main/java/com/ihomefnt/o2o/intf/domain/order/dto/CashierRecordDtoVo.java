/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月11日
 * Description:CashierRecordDtoVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhang
 */
@Data
public class CashierRecordDtoVo {

	private Long id;

	/**
	 * 订单ID
	 */
	private Long fidOrder;

	/**
	 * 收款人ID
	 */
	private Long fidPayee;

	/**
	 * 艾积分回执单号
	 */
	private String ajbOrderNum;

	/**
	 * 支付回执单号
	 */
	private String orderNum;

	/**
	 * 收款人姓名
	 */
	private String payeeName;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 金额
	 */
	private BigDecimal money;

	/**
	 * 软装收款金额
	 */
	private BigDecimal softMoney;

	/**
	 * 硬装收款金额
	 */
	private BigDecimal hardMoney;

	/**
	 * 实退金额
	 */
	private BigDecimal refundMoney;

	/**
	 * 支付方式
	 */
	private Integer paymentMode;

	/**
	 * 支付号码（pos、银行卡号、支付宝账号、微信号等）
	 */
	private String paymentNo;

	/**
	 * 收据编号
	 */
	private String receiptNo;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 备注
	 */
	private String remark;

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
