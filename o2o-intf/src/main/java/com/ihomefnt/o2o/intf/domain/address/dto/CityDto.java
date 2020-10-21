package com.ihomefnt.o2o.intf.domain.address.dto;

import lombok.Data;

import java.util.List;

@Data
public class CityDto {

	private Integer cityId;
	private String cityName;
	private List<DistrictDto> districtList;
	
}
