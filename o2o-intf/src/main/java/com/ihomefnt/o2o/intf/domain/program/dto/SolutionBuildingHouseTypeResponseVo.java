package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 置家顾问版块户型信息
 * @author ZHAO
 */
@Data
public class SolutionBuildingHouseTypeResponseVo implements Serializable {
	private Integer houseTypeId;//户型ID
	
	private String houseTypeName;//户型名称
	
	private String houseArea;//户型面积
	
	private List<SolutionSketchInfoResponseVo> seriesProgramList;//方案列表
}
