package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.util.List;

/**
 * 订单物流信息
 * @author ZHAO
 */
@Data
public class OrderLogisticResultDto {
	private Integer orderId;// 订单id
	
	private String orderNum;//订单编号
	
	private Integer orderType;//订单类型
	
	private Integer productCount;//商品总件数
	
	private List<OrderProductDeliveryInfoDto> infos;// 订单关联的快递信息
}
