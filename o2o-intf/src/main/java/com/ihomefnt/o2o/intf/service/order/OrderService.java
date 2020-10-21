package com.ihomefnt.o2o.intf.service.order;

import com.ihomefnt.o2o.intf.domain.order.dto.*;
import com.ihomefnt.o2o.intf.domain.order.vo.request.OrderAuthRequestVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.LogisticListResponse;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderAuthResponseVo;
import com.ihomefnt.o2o.intf.domain.order.vo.response.OrderResponse;

import java.util.List;


/**
 * Created by shirely_geng on 15-1-22.
 */
public interface OrderService {

    TOrder queryOrderByOrderId(Long orderId);
    
	TOrder queryMyOrderByOrderId(Long orderId);

	List<Consignee> queryMyConsignee(Long userId);
	
	Double selPayedMoneyByOrderId(Long orderId);
	
	Double selPayedMoneyByOrderIdAndOrderStatus(Long orderId,Integer orderStatus);
	
	/**
	 * 获取艺术品支付宝支付回调
	* @param @return
	* @return String 
	* @author Charl
	 */
	String getSubAlipayNotifyUrl290();
	
	/**
	 * 根据订单ID获取所有已付金额
	 * @param orderId 订单ID
	 */
	Double queryPayedMoneyByOrderId(Long orderId);

	/**
	 * 根据配置项ID获取可选金额
	 * @param configId 配置项ID
	 */
	List<Double> querySelSum(Long configId);

	/**
	 * 根据订单ID获取所有支付明细
	 * @param orderId 订单ID
	 */
	List<OrderPayRecord> querySubOrderPay(Long orderId);
	
	/**
	 * 根据订单ID查询所有物流信息
	 * @param orderId
	 * @param width
	 * @return
	 */
	LogisticListResponse queryOrderDeliveryInfoByOrderId(Integer orderId, Integer width);
	
	String goUnionPay(Long orderId, String accessToken,String host);

	/**
	 * 根据订单id来查询订单详情
	 *
	 * @param orderId
	 *            订单Id
	 * @return
	 */
	OrderDtoVo queryOrderDetail(Integer orderId);

	/**
	 * 根据订单id来查询订单详情
	 *
	 * @param orderId
	 *            订单Id
	 * @return
	 */
	OrderResponse queryArtOrderDetail(Integer orderId);

	List<OrderDtoVo> queryAlladdinOrderList(Integer userId);


	/**
	 * 获取订单未支付金额
	 * @param orderNum
	 * @return
	 */
	OrderPayBalanceVo getOrderPayBalance(String orderNum);

	OrderAuthResponseVo orderAuth(OrderAuthRequestVo orderAuthRequestVo);
}