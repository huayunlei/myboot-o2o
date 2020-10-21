package com.ihomefnt.o2o.intf.manager.util.order;

import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstants;

/**
 * Created by liguolin on 2017/6/13. 新老订单路由规则
 */
public class NewAndOldOrderInfoRoutingRule {


	public static int orderSystemRoutingRule(Integer orderId) {
		if (null == orderId) {
			return 0;
		}
		if (orderId <= OrderConstants.CRITICAL_VALUE_UPPER && orderId >= OrderConstants.CRITICAL_VALUE_LOWER) {
			return OrderConstants.ORDER_SYSTEM_SOURCE_ALADDIN;
		}
		return OrderConstants.ORDER_SYSTEM_SOURCE_OMS;
	}

	public static int orderSystemRoutingRule(String orderNum) {
		Integer _orderNum = null;
		if (null == orderNum) {
			return 0;
		}
		try {
			_orderNum = Integer.parseInt(orderNum);
		} catch (NumberFormatException e) {
			return OrderConstants.ORDER_SYSTEM_SOURCE_OMS;
		}
		if (_orderNum <= OrderConstants.CRITICAL_VALUE_UPPER && _orderNum >= OrderConstants.CRITICAL_VALUE_LOWER) {
			return OrderConstants.ORDER_SYSTEM_SOURCE_ALADDIN;
		}
		return OrderConstants.ORDER_SYSTEM_SOURCE_OMS;
	}
}
