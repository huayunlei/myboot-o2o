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
@ApiModel("硬装标准请求参数")
public class DemandConfirmationRequest extends HttpBaseRequest{

	@ApiModelProperty("订单ID")
	private Integer orderId;
	
	@ApiModelProperty("反馈意见")
	private String feedBack;

}
