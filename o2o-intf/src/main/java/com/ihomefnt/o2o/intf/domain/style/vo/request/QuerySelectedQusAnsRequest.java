package com.ihomefnt.o2o.intf.domain.style.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("已选问题答案查询参数")
@Data
public class QuerySelectedQusAnsRequest extends HttpBaseRequest {

	@ApiModelProperty("风格问题提交记录ID")
	private Integer commitRecordId;//风格问题提交记录主键

	@ApiModelProperty("订单Id")
	private Integer orderId;

	@ApiModelProperty("户型id")
	private Integer houseTypeId;


}
