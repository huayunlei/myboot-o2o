/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月16日
 * Description:HttpGroupRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.lechange.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("设备参数")
public class HttpGroupRequest extends HttpBaseRequest {

	@ApiModelProperty("设备分组ID")
	private Long groupId;
}
