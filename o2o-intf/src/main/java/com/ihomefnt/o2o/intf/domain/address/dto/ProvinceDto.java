package com.ihomefnt.o2o.intf.domain.address.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProvinceDto {

	private Integer provinceId;
	private String provinceName;
	private List<CityDto> cityList;
	
}
