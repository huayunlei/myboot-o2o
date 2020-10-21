package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 楼盘信息
 * @author ZHAO
 */
@Data
@Accessors(chain = true)
public class BuildingInfo {
	
	private String companyName = "";//公司名称
	
	private Integer buildingId = 0;//楼盘项目ID
	
	private String buildingName = "";//楼盘项目名称
	
	private String address = "";//楼盘地址
	
	private List<BuildingZoneInfo> zoneList = Lists.newArrayList();//分区
	
	private List<BuildingLayoutInfo> apartmentList = Lists.newArrayList();//户型
}
