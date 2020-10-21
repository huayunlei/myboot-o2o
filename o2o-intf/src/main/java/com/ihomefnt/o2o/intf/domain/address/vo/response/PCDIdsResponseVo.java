package com.ihomefnt.o2o.intf.domain.address.vo.response;

import lombok.Data;

@Data
public class PCDIdsResponseVo {
	private Integer provinceId;
	private Integer cityId;
	private Integer districtId;
	private String provinceName;
	private String cityName;
	private String districtName;
}
