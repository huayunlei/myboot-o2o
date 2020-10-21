/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月23日
 * Description:TransferProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.shareorder;

import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.TransferDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.shareorder.TransferProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhang
 */
@Service
public class TransferProxyImpl implements TransferProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@SuppressWarnings("unchecked")
	@Override
	public PageResponse<TransferDto> getTransferDtoList(Map<String, Object> params) {
		if (params == null) {
			return null;
		}
		HttpBaseResponse<PageResponse<TransferDto>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_TRANSFER_DTO_LIST, params,
					new TypeReference<HttpBaseResponse<PageResponse<TransferDto>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

}
