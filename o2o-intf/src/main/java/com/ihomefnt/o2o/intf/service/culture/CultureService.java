package com.ihomefnt.o2o.intf.service.culture;

import com.ihomefnt.o2o.intf.domain.culture.vo.request.CultureDetailRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.request.CultureConsumeCodeRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.request.OrderCreateRequestVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CreateOrderResponseVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CultureCommodityResponseVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.CultureConsumeCodeResponseVo;
import com.ihomefnt.o2o.intf.domain.culture.vo.response.OrderConfirmResponseVo;

public interface CultureService {
	
	/**
	 * 跟心用户购买记录
	 * @param userId
	 * @param productId
	 * @param status
	 * @return
	 */
	boolean updateUserPurchaseCultureRecord(Integer userId,Integer productId,Integer status);

	CultureCommodityResponseVo getCultureDetail(CultureDetailRequestVo requestVo);

	OrderConfirmResponseVo confirmCultureOrder(CultureDetailRequestVo requestVo);

	CreateOrderResponseVo createCultureOrder(OrderCreateRequestVo requestVo);

    CultureConsumeCodeResponseVo getGenerateCode(CultureConsumeCodeRequestVo requestVo);
}
