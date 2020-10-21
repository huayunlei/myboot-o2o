package com.ihomefnt.o2o.intf.proxy.order;

import com.ihomefnt.o2o.intf.domain.order.dto.OrderLogisticResultDto;

/**
 * 订单  公共服务端
 * @author ZHAO
 */
public interface OrderHomeProxy {
	/**
	 * 根据订单ID查询所有物流信息
	 * @param orderId
	 * @return
	 */
	OrderLogisticResultDto queryOrderDeliveryInfoByOrderId(Integer orderId);
}
