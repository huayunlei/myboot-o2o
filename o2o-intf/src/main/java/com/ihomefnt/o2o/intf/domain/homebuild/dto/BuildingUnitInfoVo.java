package com.ihomefnt.o2o.intf.domain.homebuild.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 单元号
 * Author: ZHAO
 * Date: 2018年5月9日
 */
@Accessors(chain = true)
@Data
public class BuildingUnitInfoVo implements Serializable {
	private String unitNo;//单元号
	
	private List<HouseLocationInfoVo> roomInfoVos;//房号信息

}
