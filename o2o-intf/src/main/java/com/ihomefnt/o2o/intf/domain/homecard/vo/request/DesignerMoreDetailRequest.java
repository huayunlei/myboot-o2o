/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年3月2日
 * Description:DesignerMoreDetailRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("设计师详情请求参数")
public class DesignerMoreDetailRequest extends HttpBaseRequest {

	@ApiModelProperty("设计师id")
	private Integer designerId;
	
	@ApiModelProperty("第几页")
	private Integer pageNo;
	
	@ApiModelProperty("每页大小")
	private Integer pageSize;
}
