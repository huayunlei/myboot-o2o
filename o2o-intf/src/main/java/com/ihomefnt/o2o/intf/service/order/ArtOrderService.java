package com.ihomefnt.o2o.intf.service.order;

import com.ihomefnt.o2o.intf.domain.order.vo.request.DeliverOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderListRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpOrderRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.response.*;
import com.ihomefnt.o2o.intf.domain.common.http.PageModel;

import java.util.List;

public interface ArtOrderService {
    HttpOrderResponse getSubOrderforAlipay(HttpOrderRequest orderRequest);

    HttpOrderResponse queryOrderPay(HttpOrderRequest orderRequest);

    HttpSubOrderPayResponse createSubOrderPay(HttpOrderRequest orderRequest);

    HttpSubOrderPayInfoResponse querySubOrderPay(HttpOrderRequest orderRequest);

    PageModel orderList295(HttpOrderListRequest orderRequest);

     List<OrderResponse> queryMasterOrderList(Integer userId);

    OrderResponse getOrderDetail298(HttpOrderRequest orderRequest);

    void orderCancel(HttpOrderRequest orderRequest);

    OrderPayInfoForAlipayResponseVo getSubOrderforAlipayReform(HttpOrderRequest orderRequest);

    OrderResponse queryDeliveryInfoByOrderId(DeliverOrderRequest orderRequest);

    OrderResponse queryOrderDetail(HttpOrderRequest request);
}
