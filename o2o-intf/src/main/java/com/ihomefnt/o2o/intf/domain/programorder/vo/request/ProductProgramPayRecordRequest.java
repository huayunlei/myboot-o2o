package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("产品方案-订单支付明细请求参数")
public class ProductProgramPayRecordRequest extends HttpBaseRequest{

	@ApiModelProperty("订单Id")
	private Integer orderId;

	@ApiModelProperty("当前第几页")
	private Integer pageNo;

	@ApiModelProperty("每页多少条")
	private Integer pageSize;
}
