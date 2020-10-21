package com.ihomefnt.o2o.intf.domain.culture.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("创建订单返回vo")
public class CreateOrderResponseVo {

	@ApiModelProperty("订单id")
	private Integer orderId;
}
