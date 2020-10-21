package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 空间方案
 * @author ZHAO
 */
@Data
public class SolutionInfoAndRoomVo {
	private Integer solutionId;//方案id
	
	private String solutionName;//方案名称
	
	private BigDecimal solutionDiscount;//方案折扣
	
	private Integer solutionStyleId;//方案风格id
	
	private String solutionStyleName;//方案风格名称
	
	private Integer solutionRoomId;//方案空间id
	
	private Integer solutionRoomTypeId;//方案空间标识id
	
	private String solutionRoomTypeName;//方案空间标识名称
	
	private Integer solutionRoomUseId;//方案空间用途id
	
	private String solutionRoomUseName;//方案空间用途名称
	
	private Integer solutionRoomItemCount;//方案空间家具数量
	
	private BigDecimal solutionRoomSalePrice;//方案空间价格

	private BigDecimal roomPrice;//方案空间价格
	
	private String solutionRoomHeadImgURL;//方案空间首图URL
	
	private Integer decorationType;// 装修类型：0硬装+软装，1纯软装
}
