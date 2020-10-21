package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompareSolutionRoomItem {
	private String secondCategoryName;//二级类目名称
	
	private List<SolutionRoomItemVo> itemList;//家具列表

}
