package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 置家顾问版块楼盘方案数据
 * @author ZHAO
 */
@Data
public class SolutionBuildingProgramResponseVo implements Serializable{
	private Integer buildingId;//楼盘ID
	
	private String buildingName;//楼盘名称
	
	private String buildingAddress;//楼盘地址
	
	private List<SolutionZoneInfoVo> zoneList;//分区列表
}
