package com.ihomefnt.o2o.intf.domain.homebuild.vo.response;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.BuildingCityInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 楼盘省份
 * Author: ZHAO
 * Date: 2018年4月12日
 */
@Data
@Accessors(chain = true)
public class BuildingProvinceResponse {
	private Integer provinceId;//省份ID
	
	private String provinceName;//省份名称
	
	private List<BuildingCityInfo> cityList;//城市
}
