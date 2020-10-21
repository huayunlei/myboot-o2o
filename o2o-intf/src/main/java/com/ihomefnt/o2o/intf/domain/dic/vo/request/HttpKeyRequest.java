/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月5日
 * Description:HttpKeyRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.dic.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("字典表关键字请求参数")
public class HttpKeyRequest extends HttpBaseRequest {

	@ApiModelProperty("1:表示定金条款")
	private Integer keyType;

}
