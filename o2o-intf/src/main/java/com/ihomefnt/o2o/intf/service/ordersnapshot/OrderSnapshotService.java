package com.ihomefnt.o2o.intf.service.ordersnapshot;

import com.ihomefnt.o2o.intf.domain.ordersnapshot.dto.OrderSnapshotProductResponse;
import com.ihomefnt.o2o.intf.domain.ordersnapshot.dto.OrderSnapshotResponse;

/**
 * 订单快照
 * @author ZHAO
 */
public interface OrderSnapshotService {
	/**
	 * 根据订单编号和商品id查询订单快照商品信息
	 * @param orderNum 订单编号
	 * @param orderType 订单类型
	 * @param productId 商品id
	 * @return
	 */
	OrderSnapshotProductResponse queryProductInfo(String orderNum, Integer orderType, Integer productId);
	
	/**
	 * 根据订单编号查询订单快照
	 * @param orderNum 订单编号
	 * @param orderType 订单类型
	 * @return
	 */
	OrderSnapshotResponse querySnapshotsByOrderNum(String orderNum, Integer orderType);
}
