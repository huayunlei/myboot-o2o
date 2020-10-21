/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年2月1日
 * Description:HttpSearchDeviceListRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.lechange.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhang
 */
@Data
@Accessors(chain = true)
@ApiModel("搜索设备列表参数")
public class HttpSearchDeviceListRequest {

	@ApiModelProperty("设备Id")
	private String deviceId;

	@ApiModelProperty("设备状态")
	private Integer status;
}
