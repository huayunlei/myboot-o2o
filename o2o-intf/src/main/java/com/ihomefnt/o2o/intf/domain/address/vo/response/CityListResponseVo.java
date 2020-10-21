package com.ihomefnt.o2o.intf.domain.address.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class CityListResponseVo {

	private List<CityDetailResponseVo> cityList;//城市列表
	private CityDetailResponseVo defaultCity;//默认城市
	private String prompt;
}
