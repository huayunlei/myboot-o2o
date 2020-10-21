package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 单元
 * Author: ZHAO
 * Date: 2018年5月9日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BuildingUnitInfo {
	private String unitNo = "";//单元号
	
	private List<BuildingRoomInfo> roomNoList;//房号集合

}
