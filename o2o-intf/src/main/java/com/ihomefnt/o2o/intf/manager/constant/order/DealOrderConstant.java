package com.ihomefnt.o2o.intf.manager.constant.order;

/**
 * Created by hvk687 on 9/29/15.
 */
public interface DealOrderConstant {
    int ORDER_CREATED = 2;//订单创建成功,等待支付
    int ORDER_PAYED = 5;//订单已经支付,待领取
    int PRODUCT_PICKED = 6;//产品已经领取
}
