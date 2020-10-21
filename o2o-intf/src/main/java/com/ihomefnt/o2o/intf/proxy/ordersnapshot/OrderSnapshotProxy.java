package com.ihomefnt.o2o.intf.proxy.ordersnapshot;

import com.ihomefnt.common.api.ResponseVo;

/**
 * 订单快照代理类
 * @author ZHAO
 */
public interface OrderSnapshotProxy {

	/**
	 * 根据订单编号和商品id查询订单快照商品信息
	 * @param param
	 * @return
	 */
	ResponseVo<?> queryProductInfo(Object param);
	
	/**
	 * 根据订单编号查询订单快照
	 * @param param
	 * @return
	 */
	ResponseVo<?> querySnapshotsByOrderNum(Object param);
	
}
