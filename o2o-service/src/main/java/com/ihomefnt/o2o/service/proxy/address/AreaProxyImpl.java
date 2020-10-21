/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年7月10日
 * Description:AreaProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.address;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.address.dto.AreaDto;
import com.ihomefnt.o2o.intf.domain.address.dto.ProvinceDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.CmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.IhomeApiServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.address.AreaProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang
 * @see http://192.168.1.12:10014/cms-web/swagger/
 */
@Service
public class AreaProxyImpl implements AreaProxy {
	
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public AreaDto queryArea(Long areaId) {
		if (areaId == null) {
			return null;
		}

		ResponseVo<AreaDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_AREA_BY_ID, areaId, 
					new TypeReference<ResponseVo<AreaDto>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (null != responseVo && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}


	@Override
	public List<ProvinceDto> queryAddress() {
		ResponseVo<List<ProvinceDto>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.QUERY_ALL_ADDRESS, null, 
					new TypeReference<ResponseVo<List<ProvinceDto>>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (null != responseVo && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

    @Override
    public List<AreaDto> queryAreaList() {
		ResponseVo<List<AreaDto>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.QUERY_AREA_LIST, null,
					new TypeReference<ResponseVo<List<AreaDto>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (null != responseVo && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
    }

	/**
	 * 获取地区信息
	 * @param areaId
	 * @return
	 */
	@Override
	public String queryFullAddress(long areaId) {
		ResponseVo<String> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(CmsWebServiceNameConstants.QUERY_FULL_ADDRESS, areaId,
					new TypeReference<ResponseVo<String>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (null != responseVo && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

}
