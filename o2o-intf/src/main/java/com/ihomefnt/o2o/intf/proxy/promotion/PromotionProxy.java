/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月25日
 * Description:PromotionProxy.java
 */
package com.ihomefnt.o2o.intf.proxy.promotion;

import com.ihomefnt.o2o.intf.domain.promotion.dto.JoinPromotionResultDto;
import com.ihomefnt.o2o.intf.domain.promotion.dto.QueryPromotionResultDto;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.MarketingActivityVo;
import com.ihomefnt.o2o.intf.domain.promotion.dto.JoinPromotionVo;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 */
public interface PromotionProxy {

	/**
	 * 参加促销活动
	 * 
	 * @param param
	 * @return
	 */
	Integer confirmJoinAct(JoinPromotionVo param);

    MarketingActivityVo queryActivityById(Map<String,Object> params);

	List<MarketingActivityVo> queryEffectiveActivitiesByUserId(Integer userId, Integer width);

	QueryPromotionResultDto queryPromotionByOrderNum(Integer masterOrderId);

	List<MarketingActivityVo> queryEffectiveActivitiesByLocation(Map<String, Object> params);

    List<MarketingActivityVo> queryEffectiveActivitiesByOrderId(Map<String, Object> params);

	JoinPromotionResultDto participateActivityByOrderId(Map<String, Object> params);

    List<Long> checkIsCanJoinAct(Integer actCode, List<Integer> orderIds);

}
