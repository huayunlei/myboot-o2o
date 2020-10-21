/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月6日
 * Description:HttpDeviceRequest.java 
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
@ApiModel("绑定设备参数")
public class HttpDeviceRequest extends HttpBaseRequest {

	@ApiModelProperty("设备Id")
	private String deviceId;

	@ApiModelProperty("设备验证码")
	private String code;

}
