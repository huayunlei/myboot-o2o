package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

/**
 * 家具分类信息
 * Author: ZHAO
 * Date: 2018年6月22日
 */
@Data
public class FurnitureClassifyInfo {
	private String classTwoName;//二级类目名称
	
	private List<FurnitureEntity> furnitureList;//家具信息
}
