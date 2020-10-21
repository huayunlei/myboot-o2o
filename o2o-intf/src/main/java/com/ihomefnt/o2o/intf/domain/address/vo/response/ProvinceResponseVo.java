package com.ihomefnt.o2o.intf.domain.address.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class ProvinceResponseVo {

	private Integer pid;
	private String provinceName;
	private List<CityResponseVo> cityList;
	
}
