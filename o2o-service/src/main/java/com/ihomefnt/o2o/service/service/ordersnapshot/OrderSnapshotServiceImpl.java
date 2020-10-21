package com.ihomefnt.o2o.service.service.ordersnapshot;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.proxy.ordersnapshot.OrderSnapshotProxy;
import com.ihomefnt.o2o.intf.service.ordersnapshot.OrderSnapshotService;
import com.ihomefnt.o2o.intf.domain.ordersnapshot.dto.OrderSnapshotProductResponse;
import com.ihomefnt.o2o.intf.domain.ordersnapshot.dto.OrderSnapshotResponse;

/**
 * 订单快照业务层
 * @author ZHAO
 */
@Service
public class OrderSnapshotServiceImpl implements OrderSnapshotService{

	@Autowired
	OrderSnapshotProxy orderSnapshotProxy;
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderSnapshotServiceImpl.class);
	
	@Override
	public OrderSnapshotProductResponse queryProductInfo(String orderNum, Integer orderType, Integer productId) {
		LOG.info("queryProductInfo params: orderNum:{},orderType:{},productId:{}", orderNum, orderType, productId);
		OrderSnapshotProductResponse result = null;
		if (StringUtils.isNotEmpty(orderNum) && productId > 0) {
			JSONObject param = new JSONObject();
			param.put("orderNum", orderNum);
			param.put("orderType", orderType);
			param.put("productId", productId);
			LOG.info("OrderSnapshotServiceImpl.queryProductInfo() params:{}", JsonUtils.obj2json(param));
			ResponseVo<?> responseVo = orderSnapshotProxy.queryProductInfo(param);
			if (responseVo != null && responseVo.isSuccess()) {
				if (responseVo.getData() != null) {
					result = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()),OrderSnapshotProductResponse.class);
				}
			}
		}
		LOG.info("OrderSnapshotServiceImpl.queryProductInfo() result:{}", JsonUtils.obj2json(result));
		return result;
	}

	@Override
	public OrderSnapshotResponse querySnapshotsByOrderNum(String orderNum, Integer orderType) {
		OrderSnapshotResponse result = null;
		if (StringUtils.isNotEmpty(orderNum)) {
			JSONObject param = new JSONObject();
			param.put("orderNum", orderNum);
			param.put("orderType", orderType);
			LOG.info("OrderSnapshotServiceImpl.querySnapshotsByOrderNum() params:{}", JsonUtils.obj2json(param));
			ResponseVo<?> responseVo = orderSnapshotProxy.querySnapshotsByOrderNum(param);
			if (responseVo != null && responseVo.isSuccess()) {
				if (responseVo.getData() != null) {
					result = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), OrderSnapshotResponse.class);
				}
			}
		}
		LOG.info("OrderSnapshotServiceImpl.querySnapshotsByOrderNum() result:{}", JsonUtils.obj2json(result));
		return result;
	}

}
