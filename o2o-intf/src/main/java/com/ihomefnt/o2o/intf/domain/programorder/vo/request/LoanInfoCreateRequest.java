package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
@ApiModel("新增贷款记录请求参数")
public class LoanInfoCreateRequest extends HttpBaseRequest{

	@ApiModelProperty("订单Id")
	private Integer orderId;

	@ApiModelProperty("贷款金额")
	private BigDecimal amount;
}
