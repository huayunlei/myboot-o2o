package com.ihomefnt.o2o.intf.domain.ordersnapshot.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单快照
 * @author ZHAO
 */
@Data
public class OrderSnapshotResponse {
	private String id;  //订单快照id
	private Integer orderId;  //订单id
	private String orderNum;  //订单编号
	private Integer orderType;  //订单类型
	private BigDecimal orderPrice;  //订单金额
	private BigDecimal actualPayment;  //订单实际支付金额
	private Integer userId;  //下单人id
	private String receiverName;  //收件人姓名
	private String receiverTel;  //收件人号码
	private Integer addressId;  //收件地址
	private List<OrderSnapshotProductResponse> productInfos;  //订单快照商品信息
	private Integer delFlag;  //快照状态 （-1.删除 0.生效）
	private String createTime;  //快照创建时间
	private String updateTime;  //快照更新时间
}
