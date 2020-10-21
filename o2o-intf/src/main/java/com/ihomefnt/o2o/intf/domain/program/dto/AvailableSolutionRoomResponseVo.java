package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

/**
 * 空间标识方案
 * @author ZHAO
 */
@Data
public class AvailableSolutionRoomResponseVo {
	private List<SeriesRelateRoomVo> seriesRelateRoomVoList;//关联可选套系空间列表
}
