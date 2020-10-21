package com.ihomefnt.o2o.intf.domain.address.vo.response;

import lombok.Data;

@Data
public class LocationCityResponseVo {

	private String channelCode;
	private String channelName;
	private Integer isDefault;
    private String telephone;//服务电话
}
