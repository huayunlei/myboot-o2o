package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 空间选择方案返回值
 * @author ZHAO
 */
@Data
public class SpaceProgramListResponseVo {
	private Integer seriesId;//套系ID
	
	private String seriesName;//套系名称
	
	private Integer programNum;//可选方案数量
	
	private List<SpaceProgramResponse> spaceProgramList;//空间方案集合

	public SpaceProgramListResponseVo() {
		this.seriesId = 0;
		this.seriesName = "";
		this.programNum = 0;
		this.spaceProgramList = new ArrayList<>();
	}

}
