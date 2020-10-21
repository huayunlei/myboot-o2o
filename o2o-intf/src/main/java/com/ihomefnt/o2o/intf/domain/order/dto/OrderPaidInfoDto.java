package com.ihomefnt.o2o.intf.domain.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderPaidInfoDto {

    @ApiModelProperty("订单编号")
    private String orderNum;

    @ApiModelProperty("订单已付总额")
    private BigDecimal paidSum;
}
