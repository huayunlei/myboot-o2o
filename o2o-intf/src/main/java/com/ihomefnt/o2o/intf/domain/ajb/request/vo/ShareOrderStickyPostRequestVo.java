/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月8日
 * Description:ShareOrderStickyPostRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.ajb.request.vo;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("新家大晒精华贴请求对象")
public class ShareOrderStickyPostRequestVo extends HttpBaseRequest {

	@ApiModelProperty("新家大晒Id")
	private String shareOrderId;
}
