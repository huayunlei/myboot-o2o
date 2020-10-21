package com.ihomefnt.o2o.intf.domain.address.vo.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("PCDSNameRequestVo")
public class PCDSNameRequestVo {
	private String provinceName;
	private String cityName;
	private String districtName;
	private String streetName;
	private Long areaId;//区县Id
}
