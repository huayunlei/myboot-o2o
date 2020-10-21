package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.util.List;

/**
 * 全品家大订单方案信息
 * @author ZHAO
 */
@Data
public class AladdinProgramInfoVo {
	private Integer id;// 方案id
	
	private String name;//方案名称
	
	private String pic;// 方案头图
	
	private Integer type;//方案类型
	
	private List<AladdinStyleInfoVo> styleList;//方案风格
	
	private String seriesStr;;//方案套系
	
	private Integer furnitureCount;;//方案家具数


}
