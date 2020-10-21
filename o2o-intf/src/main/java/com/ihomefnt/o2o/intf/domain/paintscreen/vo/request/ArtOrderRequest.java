package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("画作下单")
public class ArtOrderRequest extends HttpBaseRequest{

	@ApiModelProperty("下单画作集")
	private List<OrderItemDto> orderItemDtoList;

	@ApiModelProperty("用户手机号(不用传)")
	private String mobile;

	@ApiModelProperty("用户id(不用传)")
	private Integer userId;
}
