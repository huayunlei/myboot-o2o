package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;

import java.util.List;

/**
 * 楼盘分区信息
 * Author: ZHAO
 * Date: 2018年5月9日
 */
@Data
public class BuildingZoneInfo {
    private Integer zoneId;//分区id

    private String zoneName;//分区名称

    private List<BuildingNoResponseVo> buildingNo;//楼栋信息
}
