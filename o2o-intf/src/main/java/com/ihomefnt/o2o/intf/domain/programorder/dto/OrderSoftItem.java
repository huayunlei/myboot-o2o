package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.program.dto.FurnitureEntity;
import lombok.Data;

import java.util.List;

/**
 * 软装清单
 * @author ZHAO
 */
@Data
public class OrderSoftItem {
	private List<SpaceEntity> spaceList = Lists.newArrayList();// 空间集合    软装清单
	
	private List<FurnitureEntity> softAddBagList = Lists.newArrayList();//软装增配包
	
	private List<FurnitureEntity> softIncrementList = Lists.newArrayList();//软装增减项（不展示减项）

}
