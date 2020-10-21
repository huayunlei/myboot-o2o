package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 楼盘分区信息
 * Author: ZHAO
 * Date: 2018年5月16日
 */
@Data
public class SolutionZoneInfoVo implements Serializable {
	private Integer zoneId;//分区id
	
	private String zoneName;//分区名称

	private List<SolutionBuildingHouseTypeResponseVo> houseTypeList;//户型集合
}
