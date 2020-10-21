/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月13日
 * Description:ArtistService.java 
 */
package com.ihomefnt.o2o.intf.service.artist;


import com.ihomefnt.o2o.intf.domain.art.dto.StarArtistDto;
import com.ihomefnt.o2o.intf.domain.art.dto.StarArtistListDto;
import com.ihomefnt.o2o.intf.domain.artist.dto.DesignFeeResponseVo;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.*;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.*;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderListRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;

import java.util.List;

/**
 * @author zhang
 */
public interface ArtistService {

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	ArtistLoginResponse login(ArtistLoginRequest request);

	/**
	 * 首页配置:艾佳设计师服务条款
	 * 
	 * @return
	 */
	ArtistConfigResponse config(String accessToken);

	/**
	 * 注册
	 * 
	 * @param request
	 * @return
	 */
	void register(ArtistRegisterRequest request);

	/**
	 * 我的收益
	 * 
	 * @param request
	 * @return
	 */
	DesignFeeResponseVo asset(HttpBaseRequest request);

	/**
	 * 提现申请
	 * 
	 * @param request
	 * @return
	 */
	Integer applyCash(ArtistApplyCashRequest request);

	StarArtistListDto getStarArtistList(StarArtistListRequest request);

	/**根据姓名查询小星星艺术家
	 * @param request
	 * @return
	 */
	List<StarArtistDto> getStarArtistByName(StarArtistByNameRequest request);

	/**小星星用户微信小程序注册
	 * @param request
	 * @return
	 */
	StarUserLoginResponse registerByWeChatApplet(StarUserRegisterRequest request);

	PageModel<StarOrderResponse> getMyStarOrderList(HttpOrderListRequest request);

	StarOrderResponse getMyStarOrderDetail(HttpOrderRequest request);

	ArtistInfoResponse getStarArtistById(StarArtistRequest request);

}
