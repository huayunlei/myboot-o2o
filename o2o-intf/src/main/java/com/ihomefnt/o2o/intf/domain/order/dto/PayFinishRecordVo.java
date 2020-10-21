package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayFinishRecordVo {
	
	private String id; //主键id
	
	private String orderNum; // 订单编号
	
	private Integer orderType; //订单类型
	
	private Integer batch;//支付批次
	
	private Integer payType;//支付类型
	
	private String tradeNo; //交易号
	
	private String tradeStatus; //支付状态
	
	private String buyerEmail; //买家支付帐号
	
	private BigDecimal money; //支付金额
	
	private String createTime; //记录新增时间
}
