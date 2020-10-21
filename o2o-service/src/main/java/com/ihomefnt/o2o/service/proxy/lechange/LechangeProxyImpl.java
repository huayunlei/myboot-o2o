/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年7月28日
 * Description:LechangeProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.lechange;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.lechange.LechangeProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;

/**
 * @author zhang
 */
@Service
public class LechangeProxyImpl implements LechangeProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public String queryAccessToken() {
		HttpBaseResponse<String> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.LECHANGE_QUERY_ACCESS_TOKEN, new Object(), 
					new TypeReference<HttpBaseResponse<String>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (null == responseVo) {
			return null;
		}
		return responseVo.getObj();
	}

}
