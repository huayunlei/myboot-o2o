package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 空间效果图
 * @author ZHAO
 */
@Data
public class SolutionRoomPicVo implements Serializable {
	private String solutionRoomPicURL;//方案空间效果图URL
	
	private Integer isFirst;//是否首图标志位 0不是 1是
}
