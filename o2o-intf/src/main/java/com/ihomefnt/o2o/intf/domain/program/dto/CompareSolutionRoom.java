package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;
@Data
public class CompareSolutionRoom {
	private Integer roomId;//空间id
	
	private Integer roomUsageId;//空间用途
	
	private String roomUsageName;//空间用途名称
	
	private List<CompareSolutionRoomItem> categoryList;//空间二级类目家具列表
	
	private List<SolutionRoomPicVo> solutionRoomPicVoList;//空间图片
	
}
