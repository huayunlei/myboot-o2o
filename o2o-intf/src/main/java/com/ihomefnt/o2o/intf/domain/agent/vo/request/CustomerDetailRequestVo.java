package com.ihomefnt.o2o.intf.domain.agent.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户详情请求参数
 * @author ZHAO
 */
@Data
@ApiModel("客户详情请求参数")
public class CustomerDetailRequestVo extends HttpBaseRequest {
	@ApiModelProperty("客户Id")
	private Integer customerId;

	@ApiModelProperty("buildingId")
	private Integer buildingId;

}
