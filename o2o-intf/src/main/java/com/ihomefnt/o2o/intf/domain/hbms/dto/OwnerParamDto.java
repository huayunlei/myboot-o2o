package com.ihomefnt.o2o.intf.domain.hbms.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author wangjin
 *
 */
@Data
@ApiModel(description = "获取项目工艺流程参数信息")
public class OwnerParamDto extends HttpBaseRequest{

	@ApiModelProperty(value = "订单id")
	private String orderId;

}
