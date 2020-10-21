package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 全品家订单交易信息
 * @author ZHAO
 */
@Data
public class AladdinDealInfoVo {
	private BigDecimal transtionAmount;// 应收金额
	
	private BigDecimal originalAmount ;//优惠前原价
	
	private BigDecimal payedAmount;// 已交金额
	
	private BigDecimal remainAmount;// 剩余金额
	
	private Date dstTime;//交款日期／签约日期
	
}
