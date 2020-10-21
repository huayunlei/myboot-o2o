package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单支付临界值
 * @author ZHAO
 */
@Data
public class OrderPayStage {
	private Integer payStage;
	
	private BigDecimal minPayStageRatio;
	
	private BigDecimal maxPayStageRatio;
}
