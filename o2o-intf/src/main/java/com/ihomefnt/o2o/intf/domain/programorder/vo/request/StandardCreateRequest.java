/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月4日
 * Description:StandardCreateRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhang
 */
@Data
@ApiModel("产品方案-升级包参数")
public class StandardCreateRequest {

	@ApiModelProperty("升级项SKUID")
	private Integer skuId;

	@ApiModelProperty("本次app侧只有  3:标准升级项")
	private Integer type;

}
