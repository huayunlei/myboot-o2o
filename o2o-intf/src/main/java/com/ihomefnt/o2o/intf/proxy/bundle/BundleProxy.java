/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月15日
 * Description:IBundleProxy.java 
 */
package com.ihomefnt.o2o.intf.proxy.bundle;

import com.ihomefnt.o2o.intf.domain.bundle.dto.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;

/**
 * @author zhang
 */
public interface BundleProxy {

	/**
	 * 检测bundle版本
	 * 
	 * @param vo
	 * @return
	 */
	BundleCheckResultDto checkBundle(BundleCheckParamDto vo);

	LatestBundleDto getLatestBundleByVersion(HttpBaseRequest request);

	CheckVersionBundleDto checkBundleVersion(CheckVersionBundleParamDto param);

	void updateLocation(Long logId, String city);

	Long addDownload(AppDownloadLogDto log);

}
