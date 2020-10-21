package com.ihomefnt.o2o.intf.domain.homebuild.vo.response;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingNoInfo;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingRoomInfo;
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
public class BuildingNoListResponse {
	private List<BuildingNoInfo> buildingNoList;//楼栋集合
	
	private List<BuildingRoomInfo> houseTypeList;//户型集合
}
