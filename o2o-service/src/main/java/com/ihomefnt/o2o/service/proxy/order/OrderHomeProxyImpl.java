package com.ihomefnt.o2o.service.proxy.order;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderLogisticResultDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.OmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.order.OrderHomeProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单  公共服务端
 * @author ZHAO
 */
@Service
public class OrderHomeProxyImpl implements OrderHomeProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderHomeProxyImpl.class);
	
	@Override
	public OrderLogisticResultDto queryOrderDeliveryInfoByOrderId(Integer orderId) {
		ResponseVo<?> responseVo = null;
		try{
			responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_ORDER_DELIVERY_INFO, orderId, ResponseVo.class);
		}catch(Exception e){
			return null;
		}
		OrderLogisticResultDto vo = null;
		if (responseVo.isSuccess()) {
			if (responseVo.getData() != null) {
				vo = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), OrderLogisticResultDto.class);
			}
		}
		return vo;
	}

}
