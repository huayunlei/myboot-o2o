package com.ihomefnt.o2o.intf.domain.order.vo.response;

import lombok.Data;

@Data
public class HttpOrderResponse {
    private String orderInfo;
    private String privateKey;
    
    private String orderNum;//订单编号
    private double orderPrice;//订单金额
    private double couponPay;//现金券支付金额
    private double ajbMoney; //艾积分抵用金额
}
