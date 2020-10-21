/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年12月18日
 * Description:HttpForwardRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.shareorder.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("晒家转发请求对象")
public class HttpForwardRequest extends HttpBaseRequest {

	@ApiModelProperty("晒单id")
	private String shareOrderId;

	@ApiModelProperty("晒家类型 :0 表示老晒家, 1 表示新晒家")
	private int type;
}
