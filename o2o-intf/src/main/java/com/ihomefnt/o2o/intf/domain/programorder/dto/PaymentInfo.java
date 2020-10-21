package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单支付信息
 * @author ZHAO
 */
@Data
public class PaymentInfo {
	private Integer stagePayment;// 付款阶段类型：1定金 2首付款 3中期款 4尾款
	
	private String stagePaymentDesc;// 付款阶段描述
	
	private BigDecimal minStagePayRate;// 阶段支付最低比例
	
	private BigDecimal maxStagePayRate;//阶段支付最高比例

	private String minStagePayRateDesc;// 阶段支付最低比例
	
	private String maxStagePayRateDesc;//阶段支付最高比例
	
	private BigDecimal stageTotalAmount;// 阶段应付总额 
	
	private BigDecimal stageFillAmount;// 阶段已支付金额
	
	private BigDecimal stageRemainAmount;// 阶段剩余应支付金额
}
