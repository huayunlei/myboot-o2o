/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月27日
 * Description:HttpBundleLogRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.bundle.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("bundle日志请求参数")
public class HttpBundleLogRequestVo extends HttpBaseRequest {


	@ApiModelProperty(value = "bundle版本号")
	private String bundleVersion;

	@ApiModelProperty("下载路径")
	private String url;

	@ApiModelProperty("错误日志")
	private String errorMsg;
	
	@ApiModelProperty("定位错误类型")
	private Integer errorCode;

}
