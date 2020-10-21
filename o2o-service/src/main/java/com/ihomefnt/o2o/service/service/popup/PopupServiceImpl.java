package com.ihomefnt.o2o.service.service.popup;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.loan.dto.LoanMainInfoDto;
import com.ihomefnt.o2o.intf.domain.popup.dto.PopupRuleDto;
import com.ihomefnt.o2o.intf.domain.popup.vo.request.PopupRuleRequestVo;
import com.ihomefnt.o2o.intf.domain.popup.vo.response.PopupResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.right.vo.request.OrderRightPopupRequest;
import com.ihomefnt.o2o.intf.manager.constant.home.MasterOrderStatusEnum;
import com.ihomefnt.o2o.intf.manager.constant.popup.TimeConstant;
import com.ihomefnt.o2o.intf.manager.constant.right.RightRedisConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.manager.util.common.date.DateUtil;
import com.ihomefnt.o2o.intf.proxy.popup.PopupProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.popup.PopupService;
import com.ihomefnt.o2o.service.proxy.loan.LoanProxyImpl;
import com.ihomefnt.o2o.service.proxy.programorder.ProductProgramOrderProxyImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopupServiceImpl implements PopupService {
	
	@Autowired
	private PopupProxy popupProxy;
	@Autowired
	private UserProxy userProxy;
	@Autowired
    private ProductProgramOrderProxyImpl orderProxy;
	@Autowired
    private LoanProxyImpl loanProxy;
	
	@Override
	public PopupResponseVo judgeRightPopup(OrderRightPopupRequest req) {
		if (null == req.getOrderNum() && StringUtil.isNullOrEmpty(req.getDeviceToken())) {
			// 订单编号和设备号不能同时为空
			throw new BusinessException("订单编号和设备号不能同时为空");
		}
		
		//  组装查询弹框规则的条件
		PopupRuleRequestVo popupRuleRequest = assemblePopupRuleRequest(req);
		PopupRuleDto dto = popupProxy.queryPopupRuleByParams(popupRuleRequest);
		
		PopupResponseVo response = getJudgeRightPopup(dto, popupRuleRequest, req);
		if (popupRuleRequest != null && popupRuleRequest.getLoanId() != null) {
			response.setLoanId(popupRuleRequest.getLoanId());
		}
		return response;
	}

	private PopupResponseVo getJudgeRightPopup(PopupRuleDto dto, PopupRuleRequestVo popupRuleRequest, OrderRightPopupRequest req) {
		PopupResponseVo response = null;
		if (null != dto) {
			response = ModelMapperUtil.strictMap(dto, PopupResponseVo.class);
			if (response == null) {
				// 不弹框
				response = new PopupResponseVo();
				response.setPopup(0);
				return response;
			}
			if (1 != response.getPopup()) {
				response.setPopup(0);
				return response;
			}

			
			String key = null;
			if (1 != popupRuleRequest.getLoginStatus()) {
				// 未登录
				key = getCacheKey(popupRuleRequest, RightRedisConstant.ORDER_RIGHT_POPUP_KEY+":"+req.getDeviceToken());
			} else {
				key = getCacheKey(popupRuleRequest, RightRedisConstant.ORDER_RIGHT_POPUP_KEY+":"+req.getOrderNum());
			}
			String times = AppRedisUtil.get(key);
			if (StringUtils.isNotEmpty(times)) {
				// 不弹框
				response.setPopup(0);
				return response;
			}
			response.setPopup(1);
			
			Integer time = null;
			// 弹框次数单位 0永久  1日  2月  3年
			if (response.getFrequencyUnit() != 1) {
				// 默认给1年
				time = TimeConstant.YEARTIME;
			} else {
				time = DateUtil.getRemainSecondsOneDay();
			}
			AppRedisUtil.set(key, "1", time);// 当日倒计时

		} else {
			response = new PopupResponseVo();
			// 不弹框
			response.setPopup(0);
		}
		return response;
	}

	private String getCacheKey(PopupRuleRequestVo popupRuleRequest, String keyHead) {
		return AppRedisUtil.generateNoColonCacheKey(keyHead, popupRuleRequest.getLoginStatus(), popupRuleRequest.getHasOrder()
				, popupRuleRequest.getOrderStatus(), popupRuleRequest.getGradeId(), popupRuleRequest.getOrderType()
				, popupRuleRequest.getAllMoney(), popupRuleRequest.getAijiaLoan());
	}

	private PopupRuleRequestVo assemblePopupRuleRequest(OrderRightPopupRequest req) {
		PopupRuleRequestVo popupRuleRequest = new PopupRuleRequestVo();
		// 判断是否登陆
		HttpUserInfoRequest userDto = req.getUserInfo();
		if (userDto != null && userDto.getId() != null) {
			popupRuleRequest.setLoginStatus(1);
		}
        if (1 != popupRuleRequest.getLoginStatus()) {
        	// 未登录
        	popupRuleRequest.setHasOrder(0);
        	return popupRuleRequest;
        } 
        
    	if (null == req.getOrderNum() || req.getOrderNum() <= 0) {
    		// 无订单
    		popupRuleRequest.setHasOrder(0);
    		return popupRuleRequest;
    	}
    	// 有订单
    	popupRuleRequest.setHasOrder(1);
    	
    	// 订单状态
    	AppOrderBaseInfoResponseVo orderInfo = orderProxy.queryAppOrderBaseInfo(req.getOrderNum());
    	if (orderInfo == null || orderInfo.getOrderStatus() == null) {
    		// 无订单
    		popupRuleRequest.setHasOrder(0);
    		return popupRuleRequest;
    	}
    	
    	if (3 == orderInfo.getGradeId()) {// 钻石等级
    		popupRuleRequest.setGradeId(orderInfo.getGradeId());
    		return popupRuleRequest;
    	}
    	Integer orderStatus = orderInfo.getOrderStatus();
    	popupRuleRequest.setOrderStatus(orderStatus);
		if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_CONTACT_STAGE.getStatus())) {
            //接触中的用户
			return popupRuleRequest;
		}
		
		popupRuleRequest.setGradeId(orderInfo.getGradeId());
		if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_IN_DELIVERY.getStatus()) || 
				orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_COMPLETED.getStatus())) {
			return popupRuleRequest;
		}
		
		if (orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_INTENTIONAL_PHASE.getStatus()) 
				|| orderStatus.equals(MasterOrderStatusEnum.ORDER_STATUS_DEPOSIT_PHASE.getStatus())) {
			// 意向阶段或定金阶段
			if (null == orderInfo.getOrderSaleType() || (1 != orderInfo.getOrderSaleType() && 0 != orderInfo.getOrderSaleType())) {
				//不是全品家和全品家软
				return popupRuleRequest;
			}
			// 订单类型
			popupRuleRequest.setOrderType(orderInfo.getOrderSaleType());
			return popupRuleRequest;
		}

		// 订单类型
		if (null != orderInfo.getOrderSaleType()) {
			popupRuleRequest.setOrderType(orderInfo.getOrderSaleType());
		}
		// 是否交完全款
		if (null != orderInfo.getAllMoney()) {
			popupRuleRequest.setAllMoney(orderInfo.getAllMoney());
		}
		
		List<LoanMainInfoDto> loanList = loanProxy.queryLoanInfoList(req.getOrderNum().longValue());
		// 是否已申请艾佳贷
		if (CollectionUtils.isNotEmpty(loanList)) {
			popupRuleRequest.setAijiaLoan(1);
			popupRuleRequest.setLoanId(loanList.get(0).getLoanId());
		} else {
			popupRuleRequest.setAijiaLoan(0);
		}
		return popupRuleRequest;
	}

	@Override
	public void cancelRightPopup(OrderRightPopupRequest req) {
		//  组装查询弹框规则的条件
		PopupRuleRequestVo popupRuleRequest = assemblePopupRuleRequest(req);
		
		String key = getCacheKey(popupRuleRequest, RightRedisConstant.ORDER_RIGHT_POPUP_KEY+":"+req.getOrderNum());
		AppRedisUtil.set(key, "1", TimeConstant.YEARTIME);// 永久，默认一年
	}

}
