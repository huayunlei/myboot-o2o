package com.ihomefnt.o2o.intf.domain.paintscreen.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("画作支付参数")
public class ArtPayRequest extends HttpBaseRequest{

	@ApiModelProperty("订单ID")
    private String orderId;

	@ApiModelProperty("支付来源 1微信 2支付宝")
	private Integer channelSource;

	@ApiModelProperty("订单来源1 PC网站 2 APP 3 H5")
	private Integer source = 2;

	@ApiModelProperty("订单类型 画作订单默认18")
	private Integer orderType =18;
}
