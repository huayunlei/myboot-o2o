/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月13日
 * Description:ArtistController.java 
 */
package com.ihomefnt.o2o.api.controller.artist;

import com.ihomefnt.o2o.intf.domain.artist.dto.DesignFeeResponseVo;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.ArtistApplyCashRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.ArtistLoginRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.ArtistRegisterRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtistConfigResponse;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtistLoginResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.constant.artist.ApplyEnum;
import com.ihomefnt.o2o.intf.service.artist.ArtistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 微信小程序 设计师
 * 
 * @see *原始需求:
 *      http://wiki.ihomefnt.com:8002/pages/viewpage.action?pageId=11246428<br/>
 * @see *详细设计:
 *      http://wiki.ihomefnt.com:8002/pages/viewpage.action?pageId=11246658<br/>
 * @author zhang
 */
@ApiIgnore
@RestController
@Api(tags = "【微信小程序】设计师API",hidden = true)
@RequestMapping("/artist")
public class ArtistController {

	@Autowired
	private ArtistService artistService;

	@ApiOperation(value = "申请注册", notes = "申请注册")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public HttpBaseResponse<Void> register(@RequestBody ArtistRegisterRequest request) {
		artistService.register(request);

		return HttpBaseResponse.success("亲爱的" + request.getUserName() + "，感谢您注册艾佳生活设计师！您的资料审核后，我们会以短信通知到您！");
	}

	@ApiOperation(value = "登录", notes = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public HttpBaseResponse<ArtistLoginResponse> login(@RequestBody ArtistLoginRequest request) {
		ArtistLoginResponse baseResponse = artistService.login(request);
		return HttpBaseResponse.success(baseResponse);
	}

	@ApiOperation(value = "首页配置", notes = "显示:艾佳设计师服务条款")
	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public HttpBaseResponse<ArtistConfigResponse> config(@RequestBody HttpBaseRequest request) {
		ArtistConfigResponse obj = artistService.config(request.getAccessToken());

		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "我的收益", notes = "我的收益")
	@RequestMapping(value = "/asset", method = RequestMethod.POST)
	public HttpBaseResponse<DesignFeeResponseVo> asset(@RequestBody HttpBaseRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		DesignFeeResponseVo obj = artistService.asset(request);
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "提现申请", notes = "提现申请")
	@RequestMapping(value = "/applyCash", method = RequestMethod.POST)
	public HttpBaseResponse<Void> applyCash(@RequestBody ArtistApplyCashRequest request) {
		if (request == null || request.getApplyCashMoney() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		Integer result = artistService.applyCash(request);
		if (result == null) {
			result = -1;
		}
		if (result != ApplyEnum.SUCCESS.getCode()) {
			String msg = ApplyEnum.getMsg(result);
			if (StringUtils.isBlank(msg)) {
				msg = ApplyEnum.SYS_ERROR.getMsg();
			}

			return HttpBaseResponse.fail(result.longValue(), msg);
		}
		return HttpBaseResponse.success();
	}

}
