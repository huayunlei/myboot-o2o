package com.ihomefnt.o2o.intf.domain.homecard.dto;


import lombok.Data;

/**
 * 房产信息处理结果
 * Author: ZHAO
 * Date: 2018年4月18日
 */
@Data
public class HouseInfo {

	private String buildingName = "";//楼盘项目名称
	
	private String houseTypeName = "";//户型名称
	
	private String buildingAddress = "";//地址：省份城市楼盘
	
	private String unitRoomNo = "";//楼栋单元房号
	
	private String size = "";//面积
	
	private String housePattern = "";//格局

	private String houseImage = "";//户型图
	
}
