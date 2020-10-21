/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月15日
 * Description:BundleProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.bundle;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.bundle.dto.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.MobileApiServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.bundle.BundleProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhang
 */
@Service
public class BundleProxyImpl implements BundleProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public BundleCheckResultDto checkBundle(BundleCheckParamDto param) {
		try {
			HttpBaseResponse<BundleCheckResultDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.CHECK_BUNDLE_FOR_APP, param,
	                new TypeReference<HttpBaseResponse<BundleCheckResultDto>>() {
	                });
			
			if (null != response && response.getObj() != null) {
				return response.getObj();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public LatestBundleDto getLatestBundleByVersion(HttpBaseRequest param) {
		try {
			HttpBaseResponse<LatestBundleDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_LATEST_BUNDLE_BY_VERSION, param,
	                new TypeReference<HttpBaseResponse<LatestBundleDto>>() {
	                });
			
			if (null != response && response.getObj() != null) {
				return response.getObj();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public CheckVersionBundleDto checkBundleVersion(CheckVersionBundleParamDto param) {
		try {
			HttpBaseResponse<CheckVersionBundleDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.VERSION_CHECK_BUNDLE_VERSION, param,
	                new TypeReference<HttpBaseResponse<CheckVersionBundleDto>>() {
	                });
			
			if (null != response && response.getObj() != null) {
				return response.getObj();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public void updateLocation(Long logId, String city) {
		JSONObject param = new JSONObject();
		param.put("logId", logId);
		param.put("city", city);
		try {
			strongSercviceCaller.post(WcmWebServiceNameConstants.VERSION_UPDATE_LOCATION, param,
					HttpBaseResponse.class);
			
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public Long addDownload(AppDownloadLogDto log) {
		try {
			HttpBaseResponse<Long> response = strongSercviceCaller.post(WcmWebServiceNameConstants.VERSION_ADD_DOWNLOAD, log,
	                new TypeReference<HttpBaseResponse<Long>>() {
	                });
			
			if (null != response && response.getObj() != null) {
				return response.getObj();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}
