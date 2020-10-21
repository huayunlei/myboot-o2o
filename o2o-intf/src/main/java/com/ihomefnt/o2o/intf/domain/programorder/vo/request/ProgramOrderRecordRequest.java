package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author ZHAO
 */
@Data
@ApiModel("产品方案-订单支付记录请求参数")
public class ProgramOrderRecordRequest extends HttpBaseRequest{
	@ApiModelProperty("订单编号")
	private String orderNum;

	@ApiModelProperty("交易类型   1-收款 2-退款，不传或不是这两个值则为全部")
	private Integer type;

	@ApiModelProperty
	private Long id;
	@ApiModelProperty("订单Id")
	private Integer orderId;
}
