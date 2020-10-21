package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.util.List;

/**
 * 全品家大订单户型信息
 * @author ZHAO
 */
@Data
public class AladdinHouseTypeInfoVo {
	private Integer buildingHouseId;// 户型id
	
	private String buildingHouseName;// 户型名称
	
	private Integer solutionAvailableCount;// 该户型下可用的方案数目
	
	private List<AladdinProgramInfoVo> solutionAvailableList;// 该户型下可用的方案列表

}
