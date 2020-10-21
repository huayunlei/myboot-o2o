package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.util.List;

/**
 * 订单关联的快递信息
 * @author ZHAO
 */
@Data
public class OrderProductDeliveryInfoDto {
	private String deliveryNumber;//快递编号
	
	private String deliveryName;//快递公司名称
	
	private String deliveryCode;//快递公司编码
	
	private List<Integer> productIds;//产品信息Ids

	/**
	 * 产品信息Id
	 */
	private List<String> skuIds;


}
