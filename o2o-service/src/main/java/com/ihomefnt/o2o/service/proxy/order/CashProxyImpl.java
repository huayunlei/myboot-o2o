/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月25日
 * Description:CashProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.order;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.order.dto.CashierRecordResultVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.OmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.order.CashProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhang
 */
@Service
public class CashProxyImpl implements CashProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public List<CashierRecordResultVo> queryCashierRecordsByOrderId(Integer orderId) {
		if (orderId == null) {
			return null;
		}
		ResponseVo<?> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_CASHIER_RECORDS_BY_ORDER_ID, orderId, ResponseVo.class);
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			if (responseVo.getData() != null) {
				List<CashierRecordResultVo> list = JsonUtils.json2list(JsonUtils.obj2json(responseVo.getData()), CashierRecordResultVo.class);
				return list;
			}
		}
		return null;
	
	}

}
