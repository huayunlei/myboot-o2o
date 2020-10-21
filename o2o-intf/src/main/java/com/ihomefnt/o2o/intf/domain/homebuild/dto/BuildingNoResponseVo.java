package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 楼栋号
 * Author: ZHAO
 * Date: 2018年5月9日
 */
@Data
public class BuildingNoResponseVo implements Serializable {
	private Integer buildingId;//楼栋ID
	
	private String buildingName;//楼栋名称
	
	private List<BuildingUnitInfoVo> unitVoList;//单元号

	private String expectedSubmitDate;//开发商交房时间
}
