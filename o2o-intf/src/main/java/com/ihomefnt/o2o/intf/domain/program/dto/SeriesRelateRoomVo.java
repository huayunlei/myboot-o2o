package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

/**
 * 可选套系空间方案
 * @author ZHAO
 */
@Data
public class SeriesRelateRoomVo {
	private Integer seriesId;//系列id
	
	private String seriesName;//系列名称
	
	private Integer availableRoomCount;//可选空间数量
	
	private List<SolutionInfoAndRoomVo> solutionInfoAndRoomVoList;//可选方案信息&空间列表

}
