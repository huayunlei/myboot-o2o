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
@ApiModel("产品方案支付确认用户信息请求参数")
public class AladdinUserInfoRequest extends HttpBaseRequest{
	@Deprecated
	@ApiModelProperty("房产ID 已废弃，使用 customerHouseId" )
	private Integer houseId;

	@ApiModelProperty("房产ID" )
	private Integer customerHouseId;
}
