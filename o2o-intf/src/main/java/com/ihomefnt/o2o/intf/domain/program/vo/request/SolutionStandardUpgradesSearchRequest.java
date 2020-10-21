/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月3日
 * Description:SolutionStandardUpgradesSearchRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
@ApiModel("产品方案的升级包请求参数")
public class SolutionStandardUpgradesSearchRequest extends HttpBaseRequest {

	@ApiModelProperty("订单类型(套装:9,和空间组合:10)")
	private Integer orderType;

	@ApiModelProperty("方案ID")
	private Integer programId;

	@ApiModelProperty("空间Id组合")
	private List<Integer> roomIdList;
}
