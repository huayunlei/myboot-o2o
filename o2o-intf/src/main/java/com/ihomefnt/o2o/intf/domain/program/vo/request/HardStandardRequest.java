package com.ihomefnt.o2o.intf.domain.program.vo.request;

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
public class HardStandardRequest extends HttpBaseRequest{
	@ApiModelProperty("方案ID")
	private Integer programId;
	
	@ApiModelProperty("订单ID")
	private Integer orderId;

	@ApiModelProperty("入口来源:1方案详情")
	private Integer source;
}
