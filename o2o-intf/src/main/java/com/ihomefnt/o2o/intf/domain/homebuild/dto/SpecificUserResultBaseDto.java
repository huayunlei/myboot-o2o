package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;

@Data
public class SpecificUserResultBaseDto {
	// "房产id"
	private Integer houseId;

	// "订单id"
	private Integer orderId;

	// "不可看原因"
	private String msg;

	// "code值"
	private Integer code;

}
