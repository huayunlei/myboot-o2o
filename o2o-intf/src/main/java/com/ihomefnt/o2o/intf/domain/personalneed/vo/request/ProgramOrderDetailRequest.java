/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年3月15日
 * Description:ProgramOrderDetailRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.personalneed.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@ApiModel("产品方案-订单详情参数")
@Data
public class ProgramOrderDetailRequest extends HttpBaseRequest {

	@ApiModelProperty("订单Id")
	private Integer orderId;
	
	@ApiModelProperty("是否代客订单 0否，1是")
	private Integer isValetOrder;


}
