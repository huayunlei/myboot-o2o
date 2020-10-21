package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.util.List;

/**
 * 全品家大订单楼盘、项目信息
 * @author ZHAO
 */
@Data
public class AladdinBuildingProjectInfoVo {
	private Integer buildingHouseCount;// 该楼盘下的户型数目
	
	private List<AladdinHouseTypeInfoVo> buildingHouseList;// 该楼盘下的户型列表

}
