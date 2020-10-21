/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月19日
 * Description:PromotionController.java 
 */
package com.ihomefnt.o2o.api.controller.promotion;

import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.promotion.vo.request.JoinPromotionReuqest;
import com.ihomefnt.o2o.intf.domain.promotion.vo.request.MarketingActivityRequest;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.program.PromotionErrorEnum;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.intf.service.promotion.PromotionService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhang
 */
@RestController
@Api(tags = "【产品中心-促销活动API】",hidden = true)
@RequestMapping("/promotion")
public class PromotionController {

	@Autowired
	private ProductProgramOrderService orderService;
	
	@Autowired
	private PromotionService promotionService;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "参加促销活动", notes = "参加促销活动")
	@RequestMapping(value = "/joinPromotion", method = RequestMethod.POST)
	public HttpBaseResponse<PromotionJoinResponseVo> joinPromotion(@RequestBody JoinPromotionReuqest request) {
		if (request == null || request.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		Integer obj = orderService.joinPromotion(request.getOrderId(), request.getActCodes());
		if (!obj.equals(PromotionErrorEnum.SUCCESS.getCode())) {
			String msg = PromotionErrorEnum.DEFAULT_FAIL.getMsg();
			if (StringUtils.isNotBlank(PromotionErrorEnum.getMsg(obj))) {
				msg = PromotionErrorEnum.getMsg(obj);
			}
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, msg);
		}
		PromotionJoinResponseVo result = new PromotionJoinResponseVo();
		result.setOpResult(obj);
		return HttpBaseResponse.success(result);
	}

	@ApiOperation(value = "查询促销活动是否下架", notes = "查询促销活动是否下架")
	@RequestMapping(value = "/getPromotionEffective", method = RequestMethod.POST)
	public HttpBaseResponse<PromotionEffectiveResponse> getPromotionEffective(@RequestBody HttpBaseRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}
		PromotionEffectiveResponse effectiveResponse = promotionService.getPromotionEffective(request, userDto.getId());
		return HttpBaseResponse.success(effectiveResponse);
	}

	@ApiOperation(value = "查询参加活动用户数", notes = "查询参加活动用户数")
	@RequestMapping(value = "/getActiveUserNum", method = RequestMethod.POST)
	public HttpBaseResponse<PromotionActiveUserNumResponseVo> getActiveUserNum(@RequestBody HttpBaseRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}

		PromotionActiveUserNumResponseVo obj = promotionService.getActiveUserNum(userDto.getId());
		return HttpBaseResponse.success(obj);
	}

	@ApiOperation(value = "查询我的1219活动信息", notes = "查询我的1219活动信息")
	@RequestMapping(value = "/getHomeCarnivalInfo", method = RequestMethod.POST)
	public HttpBaseResponse<HomeCarnivalInfoResponse> getHomeCarnivalInfo(@RequestBody HttpBaseRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}

		HomeCarnivalInfoResponse obj = promotionService.getHomeCarnivalInfo(request, userDto.getId());
		return HttpBaseResponse.success(obj);
	}

	/**
	 * addded by matao at 2018/5/23
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询活动详情", notes = "根据活动id")
	@RequestMapping(value = "/queryEffectiveActivityById", method = RequestMethod.POST)
	public HttpBaseResponse<MarketingActivityResponse> queryEffectiveActivityById(@RequestBody MarketingActivityRequest request) {
		if(request == null || request.getActivityId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		HttpUserInfoRequest userDto = request.getUserInfo();
        MarketingActivityVo result = promotionService.queryActivityById(request, userDto == null ? null : userDto.getId());

		MarketingActivityResponse response = new MarketingActivityResponse();
		response.setActivity(result);
		return HttpBaseResponse.success(response);
	}
	/**
	 * added by matao at 2018/5/23
	 * @param request
	 * @return
	 */
	@ApiOperation(value="根据订单编号查询活动", notes="该订单已参加和可以参加的活动")
	@RequestMapping(value = "/queryEffectiveActivitiesByOrderId", method = RequestMethod.POST)
	public HttpBaseResponse<MarketingActivitieListResponse> queryEffectiveActivitiesByOrderId(@RequestBody MarketingActivityRequest request) {
		if(request == null || request.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		List<MarketingActivityVo> result = promotionService.queryActivityByOrderId(request);
		MarketingActivitieListResponse response = new MarketingActivitieListResponse();
		response.setActivities(result);
		return HttpBaseResponse.success(response);
	}

	/**
	 * added by matao at 2018/5/23
	 * @param request
	 * @return
	 */
	@ApiOperation(value="活动首页弹窗", notes="根据token和城市名称查询活动")
	@RequestMapping(value = "/queryEffectiveActivityByAccessTokenAndLocation", method = RequestMethod.POST)
	public HttpBaseResponse<MarketingActivitieListResponse> queryEffectiveActivityByAccessTokenAndLocation(@RequestBody MarketingActivityRequest request) {
		if(null == request || request.getAccessToken()  == null || request.getLocation() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if(userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.ILLEGAL_USER);
		}

		List<MarketingActivityVo> result = promotionService.queryEffectiveActivitiesByAccessTokenAndLocation(userDto.getId(), request);
		MarketingActivitieListResponse response = new MarketingActivitieListResponse();
		response.setActivities(result);
		return HttpBaseResponse.success(response);
	}

	/**
	 * added by matao at 2018/5/23
	 * @param request
	 * @return
	 */
	@ApiOperation(value="查询该用户所有的有效活动", notes="询该用户所有的有效活动")
	@RequestMapping(value = "/queryAllActivityByUser", method = RequestMethod.POST)
	public HttpBaseResponse<MarketingActivitieListResponse> queryAllActivityByUser(@RequestBody MarketingActivityRequest request) {
		if(null == request) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if(userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.ILLEGAL_USER);
		}

		List<MarketingActivityVo> result = promotionService.queryAllActivityByUser(userDto.getId(), request);
		MarketingActivitieListResponse response = new MarketingActivitieListResponse();
		response.setActivities(result);
		return HttpBaseResponse.success(response);
	}

	/**
	 * added by matao at 2018/5/23
	 * @param request
	 * @return
	 */
	@ApiOperation(value="参加活动", notes="参加活动")
	@RequestMapping(value = "/participateActivityByOrderId", method = RequestMethod.POST)
	public HttpBaseResponse<MarketingActivitieListResponse> participateActivityByOrderId(@RequestBody MarketingActivityRequest request) {
		if(request == null || request.getActivityId() == null || request.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		JoinPromotionResponseVo result = promotionService.participateActivityByOrderId(request);
		MarketingActivitieListResponse response = new MarketingActivitieListResponse();
		return HttpBaseResponse.success(response);

	}

}
