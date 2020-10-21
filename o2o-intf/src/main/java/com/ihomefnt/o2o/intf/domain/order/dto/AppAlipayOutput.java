package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppAlipayOutput implements Serializable{

	private static final long serialVersionUID = 6568518961098302459L;
	
	/**
	 * 订单支付信息
	 */
	private String orderPayInfo;
	
	private String privateKey;

}
