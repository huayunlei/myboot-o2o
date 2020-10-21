package com.ihomefnt.o2o.intf.domain.address.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class CityResponseVo {

	private Integer cid;
	private String cityName;
	private List<DistrictResponseVo> districtList;
	
}
