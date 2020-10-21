package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 楼盘城市
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Data
@Accessors(chain = true)
public class BuildingCityInfo {
	private Integer cityId;//城市ID
	
	private String cityName;//城市名称

	private Integer provinceId;//省份ID

	private String provinceName;//省份名称

	@ApiModelProperty("城市类型 0:普通城市,1:白名单城市")
	private Integer cityType = 0;
	
	private List<BuildingInfo> buildingList;//楼盘

}
