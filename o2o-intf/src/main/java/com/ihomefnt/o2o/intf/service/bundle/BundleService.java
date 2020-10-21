/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月15日
 * Description:BundleService.java 
 */
package com.ihomefnt.o2o.intf.service.bundle;

import com.ihomefnt.o2o.intf.domain.bundle.dto.AppDownloadLogDto;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppVersionDto;
import com.ihomefnt.o2o.intf.domain.bundle.dto.LatestBundleDto;
import com.ihomefnt.o2o.intf.domain.bundle.vo.request.*;
import com.ihomefnt.o2o.intf.domain.bundle.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;

/**
 * @author zhang
 */
public interface BundleService {

	/**
	 * 检测bundle版本
	 * 
	 * @param request
	 * @return
	 */
	BundleResponseVo checkBundle(BundleRequestVo request);

	LatestBundleDto getLatestBundleByVersion(HttpBaseRequest request);

	void errorLog(HttpBundleLogRequestVo request);

	QiniuTokenResponseVo generateToken(QiniuTokenRequestVo request);

	AppVersionCheckResponseVo checkAppVersion(HttpBaseRequest request, String appId);

	CheckVersionBundleResponseVo checkBundleVersion(VersionBundleRequestVo request);
	
	AppVersionDto getLatestApp(String partnerValue, String appId);
	
	void updateLocation(Long logId,String city);
	
    Long addDownload(AppDownloadLogDto log);

    VersionAndHotUpdateResponse queryVersionAndHotUpdate(QueryVersionAndHotUpdateRequest request);

    VersionAndHotUpdateResponse queryVersionAndHotUpdateNew(QueryVersionAndHotUpdateRequest request);

    PageResponse<AppVersionResponseVo> queryAppVersionList(AppVersionPageRequestVo request);

	AppInfoResponseVo queryAppDetail(String appId);

	AppGroupDetailResponseVo queryAppGroupDetail(QueryGroupAppRequestVo request);

    void downloadCount(String appId);
}
