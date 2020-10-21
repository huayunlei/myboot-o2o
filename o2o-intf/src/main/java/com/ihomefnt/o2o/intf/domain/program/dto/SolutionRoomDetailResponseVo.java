package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 空间详情
 * @author ZHAO
 */
@Data
public class SolutionRoomDetailResponseVo {
	private Integer solutionId;//方案ID
	
	private String solutionName;//方案名称
	
	private String solutionSeriesName;//方案系列名称
	
	private String solutionStyleName;//方案风格名称
	
	private BigDecimal solutionDiscount;// 方案折扣
	
	private Integer decorationType;// 装修类型：0硬装+软装，1纯软装
	
	private Integer solutionRoomId;//方案空间id
	
	private String solutionRoomDesc;//方案空间描述
	
	private Integer solutionRoomTypeId;//方案空间标识id
	
	private String solutionRoomTypeName;//方案空间类型名称
	
	private Integer solutionRoomUseId;//方案空间用途id
	
	private String solutionRoomUseName;//方案空间用途名称
	
	private BigDecimal solutionRoomSalePrice;//方案空间价格
	
	private BigDecimal roomPrice;//方案空间价格
	
	private Integer solutionRoomItemCount;//方案空间家具数量
	
	private List<SolutionRoomPicVo> solutionRoomPicList;//方案空间图片列表
	
	private List<SolutionRoomItemVo> solutionRoomItemList;//方案空间家具列表
	
	private BigDecimal solutionRoomHardDecorationSalePrice;//方案空间硬装价格
	
	private BigDecimal solutionRoomSoftDecorationSalePrice;//方案空间软装价格
	
	private Integer subSkuCount;//方案空间可选配家具数量

}
