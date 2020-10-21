package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 置家顾问版块户型信息
 * @author ZHAO
 */
@Data
@Accessors(chain = true)
public class AdviserBuildingHouseTypeResponse implements Serializable {
	private Integer houseTypeId;//户型ID
	
	private String houseTypeName;//户型名称
	
	private String houseArea;//户型面积
	
	private List<SeriesProgramListResponse> seriesProgramList;//方案列表

	public AdviserBuildingHouseTypeResponse() {
		this.houseTypeId = -1;
		this.houseTypeName = "";
		this.houseArea = "";
		this.seriesProgramList = new ArrayList<>();
	}

}
