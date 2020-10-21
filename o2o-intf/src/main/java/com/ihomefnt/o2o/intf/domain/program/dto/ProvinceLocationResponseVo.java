package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingLocationInfoVo;
import lombok.Data;

import java.util.List;

/**
 * 省份信息
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Data
public class ProvinceLocationResponseVo {
    private Integer provinceId;//省份id

    private String province;//省份名称

    private List<BuildingLocationInfoVo> buildingList;//楼盘城市信息

}
