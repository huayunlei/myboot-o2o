package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 可选空间信息
 * Author: ZHAO
 * Date: 2018年5月2日
 */
@Data
public class SolutionReplaceRoomVo implements Serializable {
	private Integer isDefault;//是否默认 0否 1是

	private Integer roomId;//空间id
	
	private String headImgUrl;//空间头图
	
	private Integer roomUsageId;//空间用途id
	
	private String roomUsageName;//空间用途名称
}
