package com.ihomefnt.o2o.intf.domain.deal.dto;

import lombok.Data;

/**
 * Created by hvk687 on 9/25/15.
 */
@Data
public class OrderDetailResponse {
    private ProductDetail product;
    private String orderNo;
    private Integer status;//1. 待支付  2.已支付，待提取   3.已提取
    private String deadLine;//截止支付日期
    private String mobile;
}
