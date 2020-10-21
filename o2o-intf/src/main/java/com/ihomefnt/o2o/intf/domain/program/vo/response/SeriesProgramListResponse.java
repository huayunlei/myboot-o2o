package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 套系方案集合返回值
 * @author ZHAO
 */
@Data
public class SeriesProgramListResponse implements Serializable {
	private Integer seriesId;//套系ID
	
	private String seriesName;//套系名称
	
	private Integer programNum;//可选方案数量
	
	private List<ProgramResponse> programList;//方案集合

	public SeriesProgramListResponse() {
		this.seriesId = -1;
		this.seriesName = "";
		this.programNum = 0;
		this.programList = new ArrayList<>();
	}

}
