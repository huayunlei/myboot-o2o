/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月6日
 * Description:SignController.java 
 */
package com.ihomefnt.o2o.api.controller.weixin;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.weixin.dto.WeChatUserInfo;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.GetPhoneNumberRequest;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.HttpGetKRequest;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.UserInfoRequest;
import com.ihomefnt.o2o.intf.manager.constant.dic.DicConstant;
import com.ihomefnt.o2o.intf.manager.util.common.wechat.Weixinutil;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.service.weixin.WeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhang
 */
@Api(tags = "【微信API】")
@RestController
@RequestMapping(value = "/weixin")
public class SignController {

	@Autowired
	DicProxy dicProxy;

	@Autowired
	WeChatService weChatService;

	@ApiOperation(value = "sign", notes = "sign")
	@RequestMapping(value = "/sign", method = RequestMethod.POST)
	public HttpBaseResponse<Map> sign(@RequestBody HttpGetKRequest request) {
		if (null == request || StringUtils.isBlank(request.getShareUrl())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		DicDto dicVo = dicProxy.queryDicByKey(DicConstant.WEIXIN_TOKEN_FLAG);
		String weixinTokenFlag = "";
		if (dicVo != null && StringUtils.isNotBlank(dicVo.getValueDesc())) {
			weixinTokenFlag = dicVo.getValueDesc();
		}
		Map<String, String> map = Weixinutil.sign(request.getShareUrl(),request.getAppId(),request.getSecret(),weixinTokenFlag);

		return HttpBaseResponse.success(map);
	}

	@ApiOperation(value = "meetSign", notes = "meetSign")
	@RequestMapping(value = "/meetSign", method = RequestMethod.POST)
	public HttpBaseResponse<Map> meetSign(@RequestBody HttpGetKRequest request) {
		if (null == request || StringUtils.isBlank(request.getShareUrl())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		Map<String, String> map = Weixinutil.meetSign(request.getShareUrl(),request.getAppId());
		return HttpBaseResponse.success(map);
	}

	@ApiOperation(value = "weChartSign", notes = "weChartSign")
	@RequestMapping(value = "/weChartSign", method = RequestMethod.POST)
	public HttpBaseResponse<Map> weChartSign(@RequestBody HttpGetKRequest request) {
		if (null == request || StringUtils.isBlank(request.getShareUrl()) || StringUtils.isBlank(request.getWxType())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		Map<String, String> map = Weixinutil.weChartSign(request.getShareUrl(),request.getWxType());
		return HttpBaseResponse.success(map);
	}

	@ApiOperation(value = "getSessionKey", notes = "getSessionKey")
	@RequestMapping(value = "/getSessionKey", method = RequestMethod.POST)
	public HttpBaseResponse<Map> getSessionKey(@RequestBody GetPhoneNumberRequest request) {
		if (null == request || StringUtils.isBlank(request.getCode())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		Map<String, Object> map = Weixinutil.getSessionKey(request, null);
		return HttpBaseResponse.success(map);
	}

	@ApiOperation(value = "queryWeChatUserInfo", notes = "获取用户微信信息")
	@RequestMapping(value = "/queryWeChatUserInfo", method = RequestMethod.POST)
	public HttpBaseResponse<WeChatUserInfo> queryWeChatUserInfo(@RequestBody UserInfoRequest request) {
		if(request == null || StringUtils.isBlank(request.getCode())){
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		WeChatUserInfo userInfo = weChatService.getUserInfoByCode(request);
		if(userInfo==null){
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.FAILED);
		}
		return HttpBaseResponse.success(userInfo);
	}
}
