package com.ihomefnt.o2o.intf.domain.address.vo.response;

import lombok.Data;

@Data
public class TReceiveAddressResponseVo {

	private Long userId;
	private String pcdAddress;
	private String purchaserName;
	private String purchaserTel;
	private String street;
	private Long areaId;//区县Id
	private boolean defaultAddress;//默认收获地址
}
