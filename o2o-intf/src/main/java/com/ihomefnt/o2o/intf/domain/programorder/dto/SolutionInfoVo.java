/*
 * Copyright (C) 2014-2016 AiJia All rights reserved
 * Author: chong
 * Date: 2017年6月14日
 * Description:SolutionInfoVo.java 
 */
package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author chong
 */
@Data
@ApiModel("方案信息")
public class SolutionInfoVo {

	@ApiModelProperty("方案id")
	private Long id;
	
	@ApiModelProperty("方案名称")
	private String name;
	
	@ApiModelProperty("方案头图")
	private String pic;
	
	@ApiModelProperty("方案类型")
	private Integer type;
	
	@ApiModelProperty("方案风格")
	private List<AladdinStyleInfoVo> styleList;
	
	@ApiModelProperty("方案套系")
	private String seriesStr;
	
	@ApiModelProperty("方案家具数")
	private Integer furnitureCount;
	
}
