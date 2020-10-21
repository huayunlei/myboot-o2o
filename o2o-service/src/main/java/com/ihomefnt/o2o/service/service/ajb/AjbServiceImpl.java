/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月8日
 * Description:AjbServiceImpl.java 
 */
package com.ihomefnt.o2o.service.service.ajb;

import com.ihomefnt.o2o.intf.domain.ajb.dto.AjbBillNoDto;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.domain.push.dto.JpushParamDto;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.ShareOrder;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.request.HttpShareOrderPraiseRequest;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbActivityResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbRecordListResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbRecordResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.ajb.AjbConstant;
import com.ihomefnt.o2o.intf.manager.constant.ajb.AjbErrorEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.proxy.ajb.AjbProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.shareorder.ShareOrderProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.ajb.AjbService;
import com.ihomefnt.o2o.intf.service.push.PushService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang
 */
@Service
public class AjbServiceImpl implements AjbService {

	@Autowired
	private UserProxy userProxy;

	@Autowired
	private ShareOrderProxy shareOrderDao;

	@Autowired
	private AjbProxy ajbProxy;

	@Autowired
	private PushService pushService;
	
	@Autowired
	private DicProxy dicProxy;

	@Override
	@Transactional
	public void addStickyPost(String shareOrderId, String accessToken) {
		if (StringUtils.isBlank(accessToken)
				|| StringUtils.isBlank(shareOrderId)) {
			throw new BusinessException(AjbErrorEnum.DATA_EMPTY.getCode(), AjbErrorEnum.DATA_EMPTY.getMsg());
		}
		
		UserDto userDto = userProxy.getUserByToken(accessToken);
		if (userDto == null || userDto.getId() == null) {
			throw new BusinessException(AjbErrorEnum.USER_TOKEN_EMPTY.getCode(), AjbErrorEnum.USER_TOKEN_EMPTY.getMsg());
		}
		
		// 1.判断是不是 ${系统管理员} (18888888802); 
		String mobile = userDto.getMobile();	
		DicListDto dicList = dicProxy.getDicListByKey(AjbConstant.SYS_ADMIN_MOBILE_KEY);
		List<String> mobileList = new ArrayList<String>();
		if (dicList != null && CollectionUtils.isNotEmpty(dicList.getDicList())) {
			//系统管理员做成可配置
			for (DicDto dic : dicList.getDicList()) {
				mobileList.add(dic.getValueDesc());
			}
		} else {
			//如果忘记配置,那就写死
			mobileList.add(AjbConstant.SYS_ADMIN_MOBILE_DEFAULT);
		}
		//不是,返回,并提示不是系统管理员 — 结束
		if (StringUtils.isBlank(mobile) ||!mobileList.contains(mobile) ) {
			throw new BusinessException(AjbErrorEnum.SYS_ADMIN_ERROR.getCode(), AjbErrorEnum.SYS_ADMIN_ERROR.getMsg());
		}
		
		// 2.接着判断该晒家是否已经是精华贴,
		HttpShareOrderPraiseRequest shareOrderPraiseRequest = new HttpShareOrderPraiseRequest();
		shareOrderPraiseRequest.setShareOrderId(shareOrderId);
		ShareOrder shareOrder = shareOrderDao.getShareOrderById(shareOrderPraiseRequest);
		if (shareOrder == null ) {
			throw new BusinessException(MessageConstant.QUERY_EMPTY);
		}
		//是,返回并提示该贴已经是精华贴; 
		if (shareOrder.getStickyPostNum() == AjbConstant.STICKY_POST_YES) {
			throw new BusinessException(AjbErrorEnum.STICKY_POST_ERROR.getCode(), AjbErrorEnum.STICKY_POST_ERROR.getMsg());
		}
		// 判断晒家用户是否正常
		if (shareOrder.getUserId() <= 0L) {
			throw new BusinessException(AjbErrorEnum.SHARE_ORDER_USERID_ERROR.getCode(), AjbErrorEnum.SHARE_ORDER_USERID_ERROR.getMsg());
		}
		Integer userId = Integer.valueOf((int) shareOrder.getUserId());
		UserDto shareUserDto = userProxy.getUserById(userId);
		shareOrder.setStickyPostNum(AjbConstant.STICKY_POST_YES);
		// 3.不是精华贴,将该晒家更新为精华贴 
		shareOrderDao.updateShareOrder(shareOrder);
		// 4.去wcm系统 获取该活动接口 : ${获取艾积分次数} :20次 和${充值艾佳金额 }: 200
		AjbActivityResponseVo ajbActivityResponseVo = ajbProxy.queryAjbActivityByCode(AjbConstant.SHARE_ORDER_CODE);
		if (ajbActivityResponseVo == null || ajbActivityResponseVo.getActiveFlag() == AjbConstant.ACTIVITY_OVER) {
			throw new BusinessException(AjbErrorEnum.ACTIVITY_OVER_ERROR.getCode(), AjbErrorEnum.ACTIVITY_OVER_ERROR.getMsg());
		}
		Integer amount = ajbActivityResponseVo.getAmount();
		if (amount == null) {
			amount = AjbConstant.SHARE_ORDER_MONEY;
		}
		Integer rechargeNum = ajbActivityResponseVo.getRechargeNum();
		if (rechargeNum == null) {
			rechargeNum = AjbConstant.Share_ORDER_TIMES;
		}
		String remark = ajbActivityResponseVo.getRemark();
		// 5.去wcm系统 获取活动记录接口 : ${该用户该活动已经获取次数}
		String activityCode = AjbConstant.SHARE_ORDER_CODE;
		AjbRecordListResponseVo ajbRecordListResponseVo = ajbProxy.queryRecordByCodeAndUserId(userId, activityCode);
		if (ajbRecordListResponseVo == null) {
			throw new BusinessException(AjbErrorEnum.LOG_QUERY_ERROR.getCode(), AjbErrorEnum.LOG_QUERY_ERROR.getMsg());
		}
		List<AjbRecordResponseVo> recordList = ajbRecordListResponseVo.getRecordList();
		int recordSize = 0;
		if (CollectionUtils.isNotEmpty(recordList)) {
			recordSize = recordList.size();
		}
		// 6.判断用户获取次数是否大于等于 活动次数; 
		if (recordSize >= rechargeNum) {
			//如果 大于,返回,并提示晒家最多获得 ${获取艾积分次数} 艾积分奖励 –-- 结束
			throw new BusinessException(AjbErrorEnum.TIMES_OVER.getCode(), AjbErrorEnum.TIMES_OVER.getMsg());
		}
		// 7.如果小于活动次数,调用公共服务充艾积分接口 ,进行充值
		AjbBillNoDto ajbBillNo = ajbProxy.ajbActivityRecharge(userId, amount, remark,
				Integer.parseInt(activityCode));
		if (ajbBillNo == null || StringUtils.isBlank(ajbBillNo.getOrderNum())) {
			throw new BusinessException(AjbErrorEnum.AJB_RECHARGE_ERROR.getCode(), AjbErrorEnum.AJB_RECHARGE_ERROR.getMsg());
		}
		// 8.调用wcm系统 活动记录接口 ,插入本次活动记录
		boolean logResult = ajbProxy.addAjbRecord(userId, ajbBillNo.getOrderNum(), activityCode);
		if (!logResult) {
			throw new BusinessException(AjbErrorEnum.LOG_INSERT_ERROR.getCode(), AjbErrorEnum.LOG_INSERT_ERROR.getMsg());
		}
		// 9.调取 公共服务 消息推送接口;推送消息给用户
		JpushParamDto jpushRequest = new JpushParamDto();
		jpushRequest.setAlias(shareUserDto.getMobile());
		jpushRequest.setNoticeSubType(AjbConstant.NOTICE_SUB_TYPE_AJB);
		jpushRequest.setNewsTitle(AjbConstant.AJB_TITLE);
		jpushRequest.setContent(AjbConstant.AJB_CONTENT);
		Integer sendResult = pushService.sendPushPersonalMessage(jpushRequest);
		if (sendResult == null || sendResult == AjbConstant.SEND_RESULT_FAIL) {
			throw new BusinessException(AjbErrorEnum.PUSH_ERROR.getCode(), AjbErrorEnum.PUSH_ERROR.getMsg());
		}

	}

}
