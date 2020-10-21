package com.ihomefnt.o2o.intf.domain.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("订单未支付金额")
public class OrderPayBalanceVo {
	
	@ApiModelProperty("订单未支付金额")
	private BigDecimal balance;
}
