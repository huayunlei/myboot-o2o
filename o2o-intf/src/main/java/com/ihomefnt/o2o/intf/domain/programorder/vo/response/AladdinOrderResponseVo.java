package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AladdinOrderResponseVo {

    private Integer orderId;//订单编号

    private Integer houseId;//房产id,

    private BigDecimal contractAmount;//订单金额
}
