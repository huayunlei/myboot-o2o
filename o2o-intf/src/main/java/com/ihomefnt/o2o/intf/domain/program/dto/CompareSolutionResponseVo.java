package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;
@Data
public class CompareSolutionResponseVo {
	private Integer solutionId;//方案id
	
	private String solutionName;//方案名称
	
	private Integer styleId;//方案风格
	
	private String styleName;//方案风格名称
	
	private Integer seriesId;//方案系列
	
	private String seriesName;//方案系列名称

	private String solutionDesignIdea;//方案设计描述

	private String advantage;//方案优点
	
	private List<String> tagList;//方案标签
	
	private Integer decorationType;//方案装修类型
	
	private String decorationTypeString;//方案装修类型名称
	
	private List<CompareSolutionRoom> roomList;//空间列表

}
