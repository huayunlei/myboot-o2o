package com.ihomefnt.o2o.intf.domain.lechange.vo.response;

import java.util.List;

import lombok.Data;

@Data
public class DeviceInfoDetailResponseVo {
	private List<Object> devices;
	private int count;
}
