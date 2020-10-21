/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月15日
 * Description:BundleServiceImpl.java 
 */
package com.ihomefnt.o2o.service.service.bundle;

import com.ihomefnt.common.constant.MessageConstant;
import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.bundle.dto.*;
import com.ihomefnt.o2o.intf.domain.bundle.vo.request.*;
import com.ihomefnt.o2o.intf.domain.bundle.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.manager.constant.log.LogEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.proxy.bundle.BundleProxy;
import com.ihomefnt.o2o.intf.proxy.bundle.HotUpdateProxy;
import com.ihomefnt.o2o.intf.proxy.user.LogProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.bundle.BundleService;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 */
@Service
public class BundleServiceImpl implements BundleService {
	private static final Logger LOG = LoggerFactory.getLogger(BundleServiceImpl.class);
	
	@Autowired
	private BundleProxy bundleProxy;
	@Autowired
	private LogProxy logProxy;
	@Autowired
	private UserProxy userProxy;
	@Autowired
	private HotUpdateProxy hotUpdateProxy;
	@Autowired
	private ApiConfig apiConfig;
	  
	@Override
	public BundleResponseVo checkBundle(BundleRequestVo request) {
		if(request == null){
			return null;
		}
		BundleCheckParamDto vo =  ModelMapperUtil.strictMap(request, BundleCheckParamDto.class);
		BundleCheckResultDto responseVo = bundleProxy.checkBundle(vo);
		if(responseVo == null){
			return null;
		}
		BundleResponseVo response =  ModelMapperUtil.strictMap(responseVo, BundleResponseVo.class);
		
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto != null) {
			Integer userId = userDto.getId();
			// 增加日志:版本升级
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("deviceToken", request.getDeviceToken());
			params.put("mobile", userDto.getMobile());
			params.put("visitType", LogEnum.LOG_VERSION_UPDATE.getCode());
			params.put("action", LogEnum.LOG_VERSION_UPDATE.getMsg());
			params.put("userId", userId);
			params.put("appVersion", request.getAppVersion());
			params.put("osType", request.getOsType());
			params.put("pValue", request.getParterValue());
			params.put("cityCode", request.getCityCode());
			params.put("businessCode", request.getBundleVersion());
			params.put("commonValue", response.getUrl());
			params.put("deviceType", request.getDeviceType());
			params.put("systemVersion", request.getSystemVersion());
			logProxy.addLog(params);
		}
		return response;
	}

	@Override
	public LatestBundleDto getLatestBundleByVersion(HttpBaseRequest request) {
		if(request == null){
			return null;
		}
		LatestBundleDto responseVo = bundleProxy.getLatestBundleByVersion(request);
		if(responseVo == null){
			return null;
		}
		LatestBundleDto response =  ModelMapperUtil.strictMap(responseVo, LatestBundleDto.class);
		
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto != null) {
			Integer userId = userDto.getId();
			// 增加日志:版本升级
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("deviceToken", request.getDeviceToken());
			params.put("mobile", userDto.getMobile());
			params.put("visitType", LogEnum.LOG_VERSION_UPDATE.getCode());
			params.put("action", LogEnum.LOG_VERSION_UPDATE.getMsg());
			params.put("userId", userId);
			params.put("appVersion", request.getAppVersion());
			params.put("osType", request.getOsType());
			params.put("pValue", request.getParterValue());
			params.put("cityCode", request.getCityCode());
			params.put("commonValue", response.getUrl());
			params.put("deviceType", request.getDeviceType());
			params.put("systemVersion", request.getSystemVersion());
			logProxy.addLog(params);
		}
		return response;
	}

	@Override
	public void errorLog(HttpBundleLogRequestVo request) {
		Integer userId = null;
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto != null) {
			userId = userDto.getId();
		}

		// 增加日志:版本升级
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceToken", request.getDeviceToken());
		params.put("mobile", request.getMobileNum());
		params.put("visitType", LogEnum.LOG_VERSION_ERROR.getCode());
		params.put("action", LogEnum.LOG_VERSION_ERROR.getMsg());
		params.put("userId", userId);
		params.put("appVersion", request.getAppVersion());
		params.put("osType", request.getOsType());
		params.put("pValue", request.getParterValue());
		params.put("cityCode", request.getCityCode());
		params.put("businessCode", request.getBundleVersion());
		params.put("commonValue", request.getUrl());
		params.put("deviceType", request.getDeviceType());
		params.put("systemVersion", request.getSystemVersion());
		params.put("errorMsg",request.getErrorMsg());
		params.put("errorCode",request.getErrorCode());
		
		logProxy.addLog(params);
	}

	static Map<String, String> bucketToUrlMap = new HashMap<String, String>();
    static {
        bucketToUrlMap.put("aijia-product-test", "res2.ihomefnt.com");
        bucketToUrlMap.put("aijia-product", "res.ihomefnt.com");
        bucketToUrlMap.put("aijia-article","article-img.ihomefnt.com");
        bucketToUrlMap.put("aijia-user","user-img.ihomefnt.com");
    }
    
	@Override
	public QiniuTokenResponseVo generateToken(QiniuTokenRequestVo request) {
		Config.ACCESS_KEY = "0cWE2Ci38evF_wbXbHSAUt-5vXMZgqN3idgyvvMy";
        Config.SECRET_KEY = "3kBcjCfTbqEVKWZttKLae_RM0zEbYc3-Q-STnXkw";
        String uptoken = "";
        String key = "";

        if (request != null && !StringUtil.isNullOrEmpty(request.getBucketName())) {
            try {
                String downloadUrl = bucketToUrlMap.get(request.getBucketName());
                Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
                PutPolicy putPolicy = new PutPolicy(request.getBucketName());
                uptoken = putPolicy.token(mac);

                QiniuTokenResponseVo response = new QiniuTokenResponseVo();
                // generate random folder
                int m = (int) (Math.random() * 10000 + 1);
                int n = (int) (Math.random() * 10000 + 1);
                int j = (int) (Math.random() * 100000 + 1);
                key = String.valueOf(m) + "/" + String.valueOf(n) + "/" + String.valueOf(j) + ".jpg";

                // generate
                response.setToken(uptoken);
                response.setKey(key);
                response.setDownloadUrl(downloadUrl);
                
                return response;
            } catch (Exception ex) {
            	LOG.error("generateToken o2o-exception , more info :", ex);
            	return null;
            }
        }
		return null;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public AppVersionCheckResponseVo checkAppVersion(HttpBaseRequest request, String appId) {
		if (request != null && !StringUtil.isNullOrEmpty(request.getAppVersion())) {
            String clientVersion = request.getAppVersion();
            String pValue = request.getParterValue();
            if (StringUtil.isNullOrEmpty(pValue)){
                pValue = "200";
            }
            AppVersionDto tAppVersion = this.getLatestApp(pValue, appId);
            
			// 如果还是取不到,就先取默认值200
			if (tAppVersion == null) {
				tAppVersion = this.getLatestApp("200", appId);
			}

            AppVersionCheckResponseVo versionCheckResponse = new AppVersionCheckResponseVo();

            if (tAppVersion != null) {
                List<String> stringList = new ArrayList<String>();
                try {
                    JSONArray jsonArray = JSONArray.fromObject(tAppVersion.getUpdateContent());
                    if (jsonArray != null) {
                        stringList = JSONArray.toList(jsonArray, String.class);
                    }
                } catch (Exception e) {
                }
                //判断版本是否要更新
                boolean updateFlag = VersionUtil.mustUpdate(clientVersion, tAppVersion.getVersion());
                if(updateFlag){
                    // 判断版本是否需要强制更新
                    boolean forceUpdate = VersionUtil.mustUpdate(clientVersion, tAppVersion.getMustUpdateVersion());
                    versionCheckResponse.setForce(forceUpdate);
                }else{
                    versionCheckResponse.setForce(false);
                }
                versionCheckResponse.setUpgrade(updateFlag);
                versionCheckResponse.setDownloadUrl(tAppVersion.getDownload());
                versionCheckResponse.setSummary(stringList);
                versionCheckResponse.setVersion(tAppVersion.getVersion());
                if(!StringUtils.isEmpty(tAppVersion.getUpdateTime())){
					versionCheckResponse.setUpdateTimeStr(LocalDateTime.parse(tAppVersion.getUpdateTime(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")));
				}

				versionCheckResponse.setUpdateUrl(apiConfig.getMopHost()+"#/dist/"+appId);
                return versionCheckResponse;
            }
        }
		return null;
	}

	@Override
	public CheckVersionBundleResponseVo checkBundleVersion(VersionBundleRequestVo request) {
		if (request == null || StringUtils.isBlank(request.getBundleVersion())) {
			throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		
		CheckVersionBundleParamDto param = new CheckVersionBundleParamDto();
		param.setBundleVersion(request.getBundleVersion());
		param.setOsType(request.getOsType());
		CheckVersionBundleDto result = bundleProxy.checkBundleVersion(param);
		if (null != result) {
			return ModelMapperUtil.strictMap(result, CheckVersionBundleResponseVo.class);
		}
		return null;
	}

	@Override
	public AppVersionDto getLatestApp(String partnerValue, String appId) {
		return hotUpdateProxy.getLatestApp(partnerValue, appId);
	}

	@Override
	public void updateLocation(Long logId, String city) {
		bundleProxy.updateLocation(logId, city);
	}

	@Override
	public Long addDownload(AppDownloadLogDto log) {
		return bundleProxy.addDownload(log);
	}

    @Override
    public VersionAndHotUpdateResponse queryVersionAndHotUpdate(QueryVersionAndHotUpdateRequest request) {
		UpdateQueryRequest updateQueryRequest = ModelMapperUtil.strictMap(request, UpdateQueryRequest.class);
		HttpBaseResponse<HotUpdateResponse> hotUpdateResponseResponseVo = hotUpdateProxy.queryUpdate(updateQueryRequest);
		VersionAndHotUpdateResponse response = new VersionAndHotUpdateResponse();
		if (hotUpdateResponseResponseVo != null && hotUpdateResponseResponseVo.getObj() != null) {
			response.setHotUpdateResponse(hotUpdateResponseResponseVo.getObj());
		}
		response.setVersionResponse(this.checkAppVersion(request, request.getAppId()));

		return response;
    }

    @Override
    public VersionAndHotUpdateResponse queryVersionAndHotUpdateNew(QueryVersionAndHotUpdateRequest request) {
		VersionAndHotUpdateResponse response = new VersionAndHotUpdateResponse();
		response.setVersionResponse(this.checkAppVersion(request, request.getAppId()));
		// 只查询版本更新
		if (null == request.getAppVersionCode() && CollectionUtils.isEmpty(request.getModules())) {
			return response;
		}

		UpdateQueryRequest updateQueryRequest = ModelMapperUtil.strictMap(request, UpdateQueryRequest.class);
		if (null == updateQueryRequest.getAppVersionCode()) {
			throw new BusinessException(MessageConstant.PARAMS_NOT_EXISTS);
		}

		HotUpdateResponse hotUpdateResponse = hotUpdateProxy.queryHotUpdateNew(updateQueryRequest);
		if (hotUpdateResponse != null) {
			response.setHotUpdateResponse(hotUpdateResponse);
		}

		return response;
    }

	@Override
	public PageResponse<AppVersionResponseVo> queryAppVersionList(AppVersionPageRequestVo request) {
		return hotUpdateProxy.queryAppVersionList(request);
	}

    @Override
    public AppInfoResponseVo queryAppDetail(String appId) {
		return hotUpdateProxy.queryAppDetail(appId);
    }

	@Override
	public AppGroupDetailResponseVo queryAppGroupDetail(QueryGroupAppRequestVo request) {
		return hotUpdateProxy.queryAppGroupDetail(request);
	}

    @Override
    public void downloadCount(String appId) {
		hotUpdateProxy.downloadCount(appId);
    }
}
