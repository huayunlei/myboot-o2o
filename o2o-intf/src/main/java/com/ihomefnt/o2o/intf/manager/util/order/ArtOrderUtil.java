/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月23日
 * Description:ArtOrderUtil.java 
 */
package com.ihomefnt.o2o.intf.manager.util.order;

import com.ihomefnt.o2o.intf.domain.order.dto.OrderDtoVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.ArtOrderResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderDetailResp;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author zhang
 */
public class ArtOrderUtil {

	/**
	 * 预计发货时间的倒计时[待发货状态的艺术品订单才有的]
	 * 
	 * @param or
	 * @return
	 */
	public static Integer getDeliveryTime(OrderResponse or, OrderDtoVo orderDto) {
		// 为空
		if (or == null || orderDto == null) {
			return 0;
		}
		Integer orderType = or.getOrderType();
		// 艺术品订单
		if (orderType == null || !OrderConstant.ORDER_TYPE_ART.equals(orderType)) {
			return 0;
		}
		Integer state = or.getState();
		// 待发货状态
		if (state == null || !OrderConstant.ORDER_OMSSTATUS_NO_SEND.equals(state)) {
			return 0;
		}
		List<OrderDetailResp> orderDetailRespList = or.getOrderDetailRespList(); // 商品信息
		if (CollectionUtils.isEmpty(orderDetailRespList)) {
			return 0;
		}
		Integer max = 0;
		for (OrderDetailResp detail : orderDetailRespList) {
			Integer deliveryTime = detail.getDeliveryTime();// 预计发货时间的倒计时[艺术品才有]
			if (deliveryTime == null) {
				deliveryTime = 0;
			}
			if (max.intValue() < deliveryTime.intValue()) {
				max = deliveryTime/(24*3600);
			}
		}
		Date date = orderDto.getUpdateTime();
		if (date == null) {
			return 0;
		}
		int time = (int) ((System.currentTimeMillis() - date.getTime()) / (24 * 3600 * 1000));
		if (time < 0) {
			return 0;
		}
		int left = max - time;
		if (left < 0) {
			return 0;
		}
		return left;

	}


	/**
	 * 预计发货时间的倒计时[待发货状态的艺术品订单才有的]
	 *
	 * @param or
	 * @return
	 */
	public static Integer getDeliveryTime(OrderResponse or) {
		// 为空
		if (or == null) {
			return 0;
		}
		Integer orderType = or.getOrderType();
		// 艺术品订单
		if (orderType == null || !OrderConstant.ORDER_TYPE_ART.equals(orderType)) {
			return 0;
		}
		Integer state = or.getState();
		// 待发货状态
		if (state == null || !OrderConstant.ORDER_OMSSTATUS_NO_SEND.equals(state)) {
			return 0;
		}
		List<OrderDetailResp> orderDetailRespList = or.getOrderDetailRespList(); // 商品信息
		if (CollectionUtils.isEmpty(orderDetailRespList)) {
			return 0;
		}
		Integer max = 0;
		for (OrderDetailResp detail : orderDetailRespList) {
			Integer deliveryTime = detail.getDeliveryTime();// 预计发货时间的倒计时[艺术品才有]
			if (deliveryTime == null) {
				deliveryTime = 0;
			}
			if (max.intValue() < deliveryTime.intValue()) {
				max = deliveryTime/(24*3600);
			}
		}
		Date date = or.getUpdateTime();
		if (date == null) {
			return 0;
		}
		int time = (int) ((System.currentTimeMillis() - date.getTime()) / (24 * 3600 * 1000));
		if (time < 0) {
			return 0;
		}
		int left = max - time;
		if (left < 0) {
			return 0;
		}
		return left;

	}

}
