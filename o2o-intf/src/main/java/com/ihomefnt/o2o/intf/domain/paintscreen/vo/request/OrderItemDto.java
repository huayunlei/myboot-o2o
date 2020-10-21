package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("画作下单参数")
public class OrderItemDto {

	@ApiModelProperty("画作ID")
	private Integer itemId;

	@ApiModelProperty("订单项类型：0:画作：1:画集")
	private Integer itemType;
}
