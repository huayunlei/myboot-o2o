package com.ihomefnt.o2o.intf.service.right;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.right.dto.OrderSingleClassifyDto;
import com.ihomefnt.o2o.intf.domain.right.vo.request.*;
import com.ihomefnt.o2o.intf.domain.right.vo.response.*;

/**
 * @author jerfan cang
 * @date 2018/9/28 13:47
 */
public interface RightService {

	CheckOrderRightsResponseVo queryOrderRightsLicense(Long orderNum);

	RightResponse queryOrderRightsDetail(OrderRightsDetailRequestVo req, String serverName);

	RightDetailResponse queryGradeClassifyInfo(ClassificationReq req, String serverName);

	/**
	 * 查询订单的单个权益分类项列表
	 * @param request
	 * @return
	 */
	OrderSingleClassifyDto queryOrderRightSingleClassify(MyOrderRightByClassifyRequest request);

	Boolean classifyRightConfirm(ConfirmRightRequest req);

	OrderRightPopupResponse judgeOrderRightPopup(OrderRightPopupRequest req);

	/**
	 * 2020版本权益宣传页
	 * @param req
	 * @return
	 */
    RightInfoResponse queryPublicityInfo(QueryMyOrderRightItemListRequest req);

	/**
	 * 2020版本我的订单权益
	 * @param req
	 * @return
	 */
	OrderRightsResponse queryMineRights(QueryMyOrderRightItemListRequest req);

	/**
	 * 更新金额显示状态
	 * @param req
	 */
    void updateMoneyHideStatus(QueryMyOrderRightItemListRequest req);
}
