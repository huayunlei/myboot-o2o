package com.ihomefnt.o2o.intf.domain.address.doo;

import lombok.Data;

@Data
public class CityDo {

	private String channelCode;
	private String channelName;
	private Integer isDefault;
    private String telephone;//服务电话
}
