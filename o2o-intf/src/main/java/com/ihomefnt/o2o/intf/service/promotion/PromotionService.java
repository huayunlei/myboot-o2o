package com.ihomefnt.o2o.intf.service.promotion;

import com.ihomefnt.o2o.intf.domain.promotion.vo.request.MarketingActivityRequest;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.JoinPromotionResponseVo;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.PromotionActiveUserNumResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.MarketingActivityVo;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.HomeCarnivalInfoResponse;
import com.ihomefnt.o2o.intf.domain.promotion.vo.response.PromotionEffectiveResponse;

import java.util.List;

/**
 * 1219置家狂欢节
 * @author ZHAO
 */
public interface PromotionService {
	/**
	 * 查询促销活动是否下架
	 * @param request
	 * @return
	 */
	PromotionEffectiveResponse getPromotionEffective(HttpBaseRequest request, Integer userId);
	
	/**
	 * 查询参加活动用户数
	 * @return
	 */
	PromotionActiveUserNumResponseVo getActiveUserNum(Integer userId);

	/**
	 * 查询我的1219活动信息
	 * @param request
	 * @param userId
	 * @return
	 */
	HomeCarnivalInfoResponse getHomeCarnivalInfo(HttpBaseRequest request, Integer userId);

    MarketingActivityVo queryActivityById(MarketingActivityRequest request, Integer userId);

	List<MarketingActivityVo> queryEffectiveActivitiesByAccessTokenAndLocation(Integer id, MarketingActivityRequest request);

	List<MarketingActivityVo> queryActivityByOrderId(MarketingActivityRequest request);

	List<MarketingActivityVo> queryAllActivityByUser(Integer userId, MarketingActivityRequest request);

	JoinPromotionResponseVo participateActivityByOrderId(MarketingActivityRequest request);
}
