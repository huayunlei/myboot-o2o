package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 可替换空间信息
 * Author: ZHAO
 * Date: 2018年5月2日
 */
@Data
public class SolutionRoomInfoVo {
	private Integer solutionRoomId;//空间id
	
	private Integer roomUsageId;//空间用途id
	
	private String roomUsageName;//空间用途名称
	
	private Integer itemCount;//家具数量
	
	private Integer solutionStyleId;//方案风格id
	
	private String solutionStyleName;//方案风格名称
	
	private Integer solutionSeriesId;//方案套系id
	
	private String solutionSeriesName;//方案套系名称
	
	private BigDecimal roomPrice;//空间价格
	
	private List<SolutionRoomPicVo> pictureList;//空间效果图
	
	private Integer isDefault;//是否默认空间 0否 1是
}
