package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 楼栋号
 * Author: ZHAO
 * Date: 2018年5月9日
 */
@Data
@Accessors(chain = true)
public class BuildingNoInfo {
	private Integer buildingNoId = 0;//楼栋ID
	
	private String buildingNo = "";//楼栋名称
	
	private List<BuildingUnitInfo> unitList;//单元集合

	private String handoverDate = "";//开发商交房时间

	private List<String> unitNoList;

}
