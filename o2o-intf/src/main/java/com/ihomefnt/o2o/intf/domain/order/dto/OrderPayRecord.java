package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderPayRecord {
	private Long rId;
	private Long orderId;//订单id
	private Integer payType;//支付类型
	private String payNum;//支付流水号
	private Double payMoney;//支付金额
	private String payTypeName;//支付类型名称
	private String subOrderNum;//子订单编号
	private Integer subOrderStatus;//子订单支付状态
	private String createTime;//创建时间
	private Date createTimeDate;//创建时间日期型
	private String updateTime;//回调时间
	
	private String requestInfo; 
	private String responseInfo; 
}
