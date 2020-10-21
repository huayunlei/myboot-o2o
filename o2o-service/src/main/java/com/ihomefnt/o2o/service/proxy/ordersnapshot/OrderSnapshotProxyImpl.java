package com.ihomefnt.o2o.service.proxy.ordersnapshot;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.proxy.ordersnapshot.OrderSnapshotProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单快照
 * 
 * @author ZHAO
 */
@Service
public class OrderSnapshotProxyImpl implements OrderSnapshotProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	/**
	 * 日志记录
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OrderSnapshotProxyImpl.class);

	/**
	 * 根据订单编号和商品id查询订单快照商品信息
	 */
	@Override
	public ResponseVo<?> queryProductInfo(Object param) {
		ResponseVo<?> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post("oms-web.orderSnapshot.queryProductInfo", param, ResponseVo.class);
		} catch (Exception e) {
			return null;
		}
		return responseVo;
	}

	/**
	 * 根据订单编号查询订单快照
	 */
	@Override
	public ResponseVo<?> querySnapshotsByOrderNum(Object param) {
		ResponseVo<?> responseVo = null;
		try {
			responseVo = strongSercviceCaller
					.post("oms-web.orderSnapshot.querySnapshotsByOrderNum", param, ResponseVo.class);
		} catch (Exception e) {
			return null;
		}
		return responseVo;
	}

}
