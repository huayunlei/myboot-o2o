package com.ihomefnt.o2o.intf.domain.agent.dto;

import lombok.Data;

/**
 * 分区信息
 * Author: ZHAO
 * Date: 2018年5月10日
 */
@Data
public class ZoneVo {
	private Integer id;//分区ID
	
	private Integer projectId;//所属项目id
	
	private String partitionName;//分区名称
	
	private Integer buildingAmount;//楼栋数量
}
