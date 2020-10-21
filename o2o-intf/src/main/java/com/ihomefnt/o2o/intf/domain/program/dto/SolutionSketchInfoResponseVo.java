package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户可选方案信息
 * @author ZHAO
 */
@Data
public class SolutionSketchInfoResponseVo implements Serializable {
	private Integer seriesId;//套系ID
	
	private String seriesName;//套系名称
	
	private Integer seriesAvailableCount;//套系可选方案数量
	
	private List<SolutionBaseInfoVo> seriesSolutionList;//套系下可选方案列表
}
