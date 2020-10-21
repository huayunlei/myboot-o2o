package com.ihomefnt.o2o.intf.domain.culture.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取唯一码Vo")
public class CultureConsumeCodeRequestVo extends HttpBaseRequest {
	
	@ApiModelProperty("订单id")
	private Integer orderId;
}
