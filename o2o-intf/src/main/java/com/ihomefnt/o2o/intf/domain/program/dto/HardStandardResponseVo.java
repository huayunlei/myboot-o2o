package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

/**
 * 硬装标准
 * @author ZHAO
 */
@Data
public class HardStandardResponseVo {
	private String seriesName;//套系名称
	
	private List<HardStandardSpaceResponseVo> spaceList;//空间集合
}
