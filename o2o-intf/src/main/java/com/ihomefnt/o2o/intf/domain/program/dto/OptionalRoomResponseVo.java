package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.util.List;

/**
 * 可替换空间信息
 * Author: ZHAO
 * Date: 2018年5月2日
 */
@Data
public class OptionalRoomResponseVo extends SolutionRoomInfoVo{
	
	private List<SolutionRoomInfoVo> roomDesignList;//空间可替换设计列表
}
