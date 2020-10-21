/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月11日
 * Description:OrderProxy.java
 */
package com.ihomefnt.o2o.intf.proxy.order;

import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.art.dto.OrderBaseInfoDto;
import com.ihomefnt.o2o.intf.domain.art.dto.OrderDto;
import com.ihomefnt.o2o.intf.domain.order.dto.*;
import com.ihomefnt.o2o.intf.domain.order.vo.CheckIfCanDeliveryConfirmVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HandleDepositMoneyRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HandleMoneyRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.OrderSimpleInfoRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.request.UpdateDeliverTimeRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.HandleDepositMoneyResponseVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderSimpleInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderDetailDto;
import com.ihomefnt.o2o.intf.domain.programorder.dto.TransactionDetail;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderStateEnum;
import com.ihomefnt.oms.trade.order.dto.solution.SolutionOrderDto;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 */
public interface OrderProxy {

	/**
	 * 创建文旅订单,返回订单id
	 * 
	 * @param order
	 *            文旅订单
	 * @return 订单id
	 */
	Integer createTripOrder(TripOrderCreateDto order);

	/**
	 * 更新订单状态
	 * 
	 * @param orderId
	 *            订单id
	 * @param orderStatus
	 *            订单状态
	 * @return true:更新成功;false:更新失败
	 */
	boolean updateOrderState(Integer orderId, OrderStateEnum orderStatus);

	/**
	 * 根据条件查询订单列表
	 * 
	 * @param condition
	 * @return
	 */
	PageModel<OrderDtoVo> queryOrderInfo(OrderInfoSearchDto condition);

	/**
	 * 根据订单编码来查询订单详情
	 * 
	 * @param orderNum
	 *            :订单编码
	 * @return
	 */
	OrderDtoVo queryOrderDetailByOrderNum(String orderNum);

	/**
	 * 查询艺术品订单详情
	 * 
	 * @param orderId
	 * @return
	 */
	ArtOrderVo queryArtOrderDetail(Integer orderId);

	/**
	 * 查询软装订单详情
	 * 
	 * @param orderId
	 * @return
	 */
	SoftOrderVo querySoftOrderDetail(Integer orderId);

	/**
	 * 查询全品家订单详情
	 * 
	 * @param orderId
	 * @return
	 */
	FamilyOrderVo queryFamilyOrderDetail(Integer orderId);

	/**
	 * 查询硬装订单详情
	 * 
	 * @param orderId
	 * @return
	 */
	HardOrderVo queryHardOrderDetail(Integer orderId);

	/**
	 * 查询文旅订单详情
	 * 
	 * @param orderId
	 * @return
	 */
	TripOrderVo queryTripOrderDetail(Integer orderId);
	
	/**
	 * 支付宝支付
	 * @param payInput
	 * @return
	 */
	AppAlipayOutput appAlipay(PayInput payInput);

	/**
	 * 获取订单已支付金额
	 * @param orderNum
	 * @return
	 */
	OrderPaidInfoDto queryOrderPaidInfo(String orderNum);
	
	/**
	 * 查询分笔支付完成记录
	 */
	List<PayFinishRecordVo> queryPayFinishedRecordList(String orderNum);
	
	/**
	 * 订单取消支付记录
	 * @param orderNum
	 * @return
	 */
	boolean cancelPay(String orderNum);

	/**
	 * 查询订单简要信息（收银台使用）
	 * @param orderId
	 * @return
	 */
	OrderDetailDto queryOrderSummaryInfo(Integer orderId);

	/**
	 * 预约线下付款
	 * @param orderId
	 * @return
	 */
	Boolean appointOfflinePay(Integer orderId);

	/**
	 * 预确认方案
	 * @param orderId
	 * @return
	 */
	Integer preConfirmSolution(Integer orderId);

	/**
	 * 付款详情
	 * @param params
	 * @return
	 */
    TransactionDetail queryPaymentRecordDetailById(Map<String, Long> params);

	SolutionOrderDto querySolutionOrderById(Integer orderId);

	OrderDtoVo queryOmsOrderDetail(Integer orderId);

	OrderResponse queryOmsArtOrderDetail(Integer orderId);

	OrderDto queryOrderByOrderNum(String orderNum);

	CheckIfCanDeliveryConfirmVo checkIfCanDeliveryConfirm(Integer orderId);

	/**
	 * 生成定金收款单
	 *
	 * @param request
	 * @return
	 */
	HandleDepositMoneyResponseVo handleDepositMoney(HandleDepositMoneyRequestVo request);

	/**
	 * 生成收款单
	 *
	 * @param request
	 * @return
	 */
	HandleDepositMoneyResponseVo handleMoney(HandleMoneyRequestVo request);

	/**
	 *
	 *
	 * @param request
	 * @return
	 */
	String updateDeliverTime(UpdateDeliverTimeRequestVo request);

	/**
	 * 取消订单
	 *
	 * @param orderId
	 * @return
	 */
	String cancelOrder(Integer orderId);

	/**
	 * 根据订单编号批量查询简单信息（订单信息，房产信息）
	 *
	 * @param orderIds
	 * @return
	 */
	List<OrderSimpleInfoResponseVo> querySimpleInfoByOrderNums(OrderSimpleInfoRequestVo orderIds);

    List<OrderBaseInfoDto> queryOrderListByUserId(Integer orderId);
}
