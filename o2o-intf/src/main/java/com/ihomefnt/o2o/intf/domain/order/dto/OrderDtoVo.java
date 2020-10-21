/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月11日
 * Description:OrderDtoVo.java 
 */
package com.ihomefnt.o2o.intf.domain.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class OrderDtoVo {

	/**
	 * 订单id
	 */
	private Integer orderId;

	@ApiModelProperty("订单备注 拼团存储商品验证")
	private String remark;

	/**
	 * 订单类型
	 */
	private Integer orderType;

	/**
	 * 订单编号全局
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
	 * 所属项目
	 */
	private Integer projectId;

	/**
	 * 楼栋号
	 */
	private String buildingNo;

	/**
	 * 房号
	 */
	private String houseNo;

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
	 * 快递编号
	 */
	private String logisticnum;

	/**
	 * 快递快递公司编号
	 */
	private String logisticcompanycode;

	/**
	 * 快递快递公司名称
	 */
	private String logisticcompanyname;

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

	// 2 已完成 5 待收货 10 待发货
	/**
	 * 订单状态,总状态
	 */
	@ApiModelProperty("订单状态 0:提交订单成功 1:处理中 2:已完成 3:已取消 4:待付款 5:待收货 6:部分付款 7:待施工 8:施工中 9:待退款 10:待发货 11:待结款 12:交易关闭 13：待接单 14：部分发货")
	private Integer state;

	/**
	 * 卖家状态
	 */
	private Integer buyerState;

	/**
	 * 买家状态
	 */
	private Integer sellerState;

	/**
	 * 订单来源
	 */
	private Integer source;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 销售id
	 */
	private Integer fidSale;

	/**
	 * 所属项目
	 */
	private Integer fidProject;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 确认时间
	 */
	private Date confirmTime;

	/**
	 * 附加属性
	 */
	private List<OrderAttrValueDtoVo> orderAttrValueOutputList;

	/**
	 * 商品清单
	 */
	private List<OrderDetailDtoVo> orderDetailDtoList;

	/**
	 * 收款记录
	 */
	private List<CashierRecordDtoVo> cashierRecordList;

	/**
	 * 退款记录
	 */
	private List<RefundRecordDtoVo> refundRecordDtoList;

	/**
	 * 操作人id
	 */
	private Integer operator;

	private boolean isAutoMatch;// 是否自由搭配
	
	/**
     * 订单渠道（1 艾商城，2 小星星小程序）
     */
    private Integer orderChannel;

}
