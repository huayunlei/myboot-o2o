package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.util.Date;

/**
 * 全品家大订单软装订单信息
 * @author ZHAO
 */
@Data
public class AladdinSoftOrderInfoVo {
	private Integer id;// 软装订单id
	
	private Integer softOrderStatus;// 软装订单状态
	
	private String softOrderStatusStr;// 软装订单状态字符串
	
	private Date dstTime;//如果未签约就是交款日期，如果已签约就是签约日期
}
