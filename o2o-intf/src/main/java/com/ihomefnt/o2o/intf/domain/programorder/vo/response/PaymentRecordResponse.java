package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 方案支付明细记录
 * @author ZHAO
 */
@Data
public class PaymentRecordResponse {
	private Integer accountRecordId;// 账本记录ID
	
	private Integer payModeCode;//支付方式： 1支付宝、2微信、3现场支付
	
	private String payMode;// 支付方式： 支付宝、微信、现场支付
	
	private Integer stagePayment;// 付款阶段类型： 1定金 2首付款 3中期款 4尾款
	
	private String stagePaymentDesc;// 付款阶段描述
	
	private BigDecimal payAmount;// 支付金额
	
	private String payResult;// 支付结果
	
	private Date payTime;// 支付时间
	
	private String payTimeYMD;// 支付时间描述  2017年02月12日
	
	private String payTimeWeek;//支付时间描述  周一
	
	private String payTimeHM;//支付时间描述 12:23分
	
	private Integer paymentType;//付款类型：1付款 2退款
}
