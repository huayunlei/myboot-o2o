package com.ihomefnt.o2o.intf.dao.order;

import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrderDetail;
import com.ihomefnt.o2o.intf.domain.product.doo.UserOrder;
import com.ihomefnt.o2o.intf.domain.order.dto.Consignee;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderPayRecord;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderSerialNo;
import com.ihomefnt.o2o.intf.domain.order.dto.TOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by shirely_geng on 15-1-23.
 */
public interface OrderDao {
    List<UserOrder> queryAllSingleUserOrder(Long userId);

    List<UserOrder> queryAllCompositeUserOrder(Long userId);

    Integer updateOrder(UserOrder userOrder);

    UserOrder queryUserOrderByOrderId(Long orderId);

    List<ProductOrderDetail> queryOrderDetailsByOrderId(Long orderId);
    
    TOrder queryOrderByOrderId(Long orderId);

    /**
     * 查询订单信息 没有状态信息
     * @param orderId
     * @return
     */
    TOrder queryMyOrderByOrderId(Long orderId);

    /**
     * 查询用户订单
     * @param userId
     * @param orderId
     * @return
     */
    List<UserOrder> queryUserOrder(Long userId,Long orderId);

    /**
     * 查询收货人信息
     * @param userId
     * @return
     */
    List<Consignee> queryMyConsignee(Long userId);

    /**
     * create order serial number into table
     * @param orderId
     */
    void createOrderSerial(Long orderId);

    /**
     * load order serial number to table
     * @param orderId
     * @return
     */
    OrderSerialNo queryOrderSerial(Long orderId);
    void setOrderNumber(String serialNo, Long orderId);
    
    Double selPayedMoneyByOrderId(Long orderId);
    
    Double selPayedMoneyByOrderIdAndOrderStatus(Long orderId,Integer orderStatus);
    
    List<UserOrder> selOrderProductImages(String orderIds);
    
	/**
	 * 根据订单ID获取所有支付明细
	 */
	List<OrderPayRecord> querySubOrderPay(Map<String, Object> param);

	/**
	 * 根据订单ID获取所有已付金额
	 */
	Double queryPayedMoneyByOrderId(Map<String, Object> param);
	
}