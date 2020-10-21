/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月15日
 * Description:BundleController.java 
 */
package com.ihomefnt.o2o.api.controller.bundler;

import com.ihomefnt.common.constant.MessageConstant;
import com.ihomefnt.o2o.intf.domain.bundle.dto.LatestBundleDto;
import com.ihomefnt.o2o.intf.domain.bundle.vo.request.*;
import com.ihomefnt.o2o.intf.domain.bundle.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBasePageResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.bundle.BundleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhang
 */
@RestController
@Api(tags = "【版本升级相关API】")
public class BundleController {
	@Autowired
	private BundleService bundleService;

	@ApiOperation(value = "检测bundle版本", notes = "升级标识:0不升级,1增量升级,2全量升级 3回滚")
	@RequestMapping(value = "/bundle/checkBundle", method = RequestMethod.POST)
	public HttpBaseResponse<BundleResponseVo> checkBundle(@RequestBody BundleRequestVo request) {
		if (request == null || StringUtils.isBlank(request.getBundleVersion())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		BundleResponseVo obj = bundleService.checkBundle(request);
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "记录错误日志", notes = "错误信息")
	@RequestMapping(value = "/bundle/errorLog", method = RequestMethod.POST)
	public HttpBaseResponse<Void> errorLog(@RequestBody HttpBundleLogRequestVo request) {
		if (request == null || StringUtils.isBlank(request.getBundleVersion())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		} 
		bundleService.errorLog(request);
		return HttpBaseResponse.success();
	}

	@ApiOperation(value = "获取对应APP版本的最高bundle版本")
	@RequestMapping(value = "/bundle/getLatestBundleByVersion", method = RequestMethod.POST)
	public HttpBaseResponse<LatestBundleDto> getLatestBundleByVersion(@RequestBody HttpBaseRequest request) {
		if (request == null || StringUtils.isBlank(request.getAppVersion())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		} 
		LatestBundleDto obj = bundleService.getLatestBundleByVersion(request);
		return HttpBaseResponse.success(obj);
	}
	
	@ApiOperation(value = "qiniutoken")
	@RequestMapping(value = "/media/qiniutoken", method = RequestMethod.GET)
	public HttpBaseResponse<QiniuTokenResponseVo> generateToken(QiniuTokenRequestVo request) {
		QiniuTokenResponseVo obj = bundleService.generateToken(request);
		return HttpBaseResponse.success(obj);
	}

	private List<String> iosTypeList = Arrays.asList("100");
	private List<String> androidTypeList = Arrays.asList("10000","200","20000","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","30001","3001");
	@ApiOperation(value = "校验APP版本")
	@RequestMapping(value = "/media/app/version", method = RequestMethod.POST)
	public HttpBaseResponse<AppVersionCheckResponseVo> checkAppVersion(@Json HttpBaseRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		String parterValue = request.getParterValue();
		String appId = null;
		if (iosTypeList.contains(parterValue)) {
			appId = "washriwvd8c6r6nz";
		} else if (androidTypeList.contains(parterValue)) {
			appId = "eax4kpvv3nsuk837";
		} else {
			return HttpBaseResponse.success();
		}
		AppVersionCheckResponseVo obj = bundleService.checkAppVersion(request, appId);
		return HttpBaseResponse.success(obj);
	}
	
	@ApiOperation(value = "更新RN的bundle版本", notes = "true表示可更新")
	@RequestMapping(value = "/media/checkBundleVersion", method = RequestMethod.POST)
	public HttpBaseResponse<CheckVersionBundleResponseVo> checkBundleVersion(@RequestBody VersionBundleRequestVo request) {
		CheckVersionBundleResponseVo obj = bundleService.checkBundleVersion(request);
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "app查询版本和热更新")
	@PostMapping(value = "/queryVersionAndHotUpdate")
	public HttpBaseResponse<VersionAndHotUpdateResponse> queryVersionAndHotUpdate(@RequestBody QueryVersionAndHotUpdateRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		VersionAndHotUpdateResponse obj = bundleService.queryVersionAndHotUpdate(request);
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "app查询版本和热更新--new")
	@PostMapping(value = "/queryVersionAndHotUpdateNew")
	public HttpBaseResponse<VersionAndHotUpdateResponse> queryVersionAndHotUpdateNew(@RequestBody QueryVersionAndHotUpdateRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		VersionAndHotUpdateResponse obj = bundleService.queryVersionAndHotUpdateNew(request);
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "查询APP版本描述列表", notes = "查询APP版本描述列表")
	@PostMapping("/version/queryAppVersionList")
	public HttpBasePageResponse<AppVersionResponseVo> queryVersionAndHotUpdateNew(@RequestBody AppVersionPageRequestVo request) {
		HttpBasePageResponse<AppVersionResponseVo> basePageResponse = new HttpBasePageResponse<AppVersionResponseVo>();

		PageResponse<AppVersionResponseVo> responseVo = bundleService.queryAppVersionList(request);
		if (null != responseVo) {
			basePageResponse.setObj(responseVo.getList());
			basePageResponse.setTotalCount(responseVo.getTotalCount());
		} else {
			basePageResponse.setTotalCount(0);
		}

		basePageResponse.setExt("查询成功");
		basePageResponse.setCode(HttpResponseCode.SUCCESS);
		return basePageResponse;
	}

	@ApiOperation("查询app详情")
	@GetMapping("/app/query-detail")
	public HttpBaseResponse<AppInfoResponseVo> queryAppDetail(String appId) {
		if (appId == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		AppInfoResponseVo obj = bundleService.queryAppDetail(appId);
		return HttpBaseResponse.success(obj);
	}

	@PostMapping("/app/group/detail")
	@ApiOperation("查询分组详情")
	public HttpBaseResponse<AppGroupDetailResponseVo> queryAppGroupDetail(@RequestBody QueryGroupAppRequestVo request) {
		if (request == null || null == request.getGroupId()) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		AppGroupDetailResponseVo obj = bundleService.queryAppGroupDetail(request);
		return HttpBaseResponse.success(obj);
	}

	@PostMapping("/app/download-count")
	@ApiOperation("app下载统计")
	public HttpBaseResponse<AppInfoResponseVo> downloadCount(@RequestBody String appId) {
		if (appId == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		bundleService.downloadCount(appId);
		return HttpBaseResponse.success();
	}

}
