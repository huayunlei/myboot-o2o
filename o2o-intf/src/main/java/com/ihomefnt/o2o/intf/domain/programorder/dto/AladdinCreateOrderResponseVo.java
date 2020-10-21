package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 创建订单返回值
 * @author ZHAO
 */
@Data
public class AladdinCreateOrderResponseVo {
	private Integer orderNum;//订单编号
	
	private Integer customerHouseId;//房产id,
	
	private BigDecimal contractAmount;//订单金额
	
	private Integer code;//响应码
}
