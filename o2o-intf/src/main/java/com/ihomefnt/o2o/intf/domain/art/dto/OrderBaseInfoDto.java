package com.ihomefnt.o2o.intf.domain.art.dto;

import lombok.Data;

@Data
public class OrderBaseInfoDto {

    // 艺术品订单编号
    private String orderNo;

    // 订单id
    private Long orderNum;

    // 订单状态
    private Integer orderStatus;

    // 订单类型
    private Integer orderType;
}
