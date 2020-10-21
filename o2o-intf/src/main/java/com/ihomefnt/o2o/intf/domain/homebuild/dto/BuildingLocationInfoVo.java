package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;

import java.util.List;

/**
 * 楼盘信息
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Data
public class BuildingLocationInfoVo {
	private Integer cityId;//城市ID
    
    private String city;//城市名称

    private Integer buildingId;//楼盘ID

    private String buildingName;//楼盘名称

    private List<BuildingZoneInfoVo> zoneList;//户型集合

}
