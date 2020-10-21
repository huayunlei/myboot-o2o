package com.ihomefnt.o2o.intf.domain.order.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class HttpSubOrderPayResponse {
    private String orderNum;//订单编号
    private double orderPrice;//订单金额
    private double alreadyPay;//已分笔支付金额
    private double remainPay;//剩余支付金额
    private List<Double> selectSum;//待选金额
}
