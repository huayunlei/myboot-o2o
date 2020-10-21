/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年6月30日
 * Description:HttpUserOrderRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("用户订单请求参数")
public class UserOrderRequestVo extends HttpBaseRequest {

	@ApiModelProperty("订单Id")
	private Integer orderId;

}
