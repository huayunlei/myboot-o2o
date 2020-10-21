package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 置家顾问版块楼盘方案数据
 * @author ZHAO
 */
@Data
public class AdviserBuildingProgramResponse implements Serializable {
	private Integer buildingId;//楼盘ID
	
	private String buildingName;//楼盘名称
	
	private String buildingAddress;//楼盘地址
	
	private List<AdviserBuildingHouseTypeResponse> houseTypeList;//户型集合

	public AdviserBuildingProgramResponse() {
		this.buildingId = -1;
		this.buildingName = "";
		this.buildingAddress = "";
		this.houseTypeList = new ArrayList<>();
	}

}
