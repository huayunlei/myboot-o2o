package com.ihomefnt.o2o.intf.domain.shareorder.vo.response;

import lombok.Data;

/**
 * 用户房屋信息
 * @author ZHAO
 */
@Data
public class UserHouseInfoResponse {
	private String cityName;//城市
	
	private String buildingAddress;//小区名称

	private String buildingInfo;//房产信息
}
