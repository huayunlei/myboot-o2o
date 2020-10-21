package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingInfo;
import lombok.Data;

import java.util.List;

/**
 * 置家顾问版块基础数据
 * @author ZHAO
 */
@Data
public class AdviserBuildingInfoResponse {
	private Integer companyId;//公司ID
	
	private String companyName;//公司名称
	
	private List<BuildingInfo> buildingInfoList;
}
