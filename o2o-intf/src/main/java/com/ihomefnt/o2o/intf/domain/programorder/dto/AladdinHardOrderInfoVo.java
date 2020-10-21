package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.util.Date;

/**
 * 全品家大订单硬装订单信息
 * @author ZHAO
 */
@Data
public class AladdinHardOrderInfoVo {
	private Integer id;//订单id
	
	private Integer hardOrderStatus;// hbms硬装订单状态
	
	private String hardOrderStatusStr;// hbms硬装订单状态字符串
	
	private Date commenceTime;//hbms开工日期

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
