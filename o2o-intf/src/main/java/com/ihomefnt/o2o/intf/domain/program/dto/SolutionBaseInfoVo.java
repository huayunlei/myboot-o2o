package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 方案信息
 * @author ZHAO
 */
@Data
public class SolutionBaseInfoVo implements Serializable {
	private Integer solutionId;//方案id
	
	private Integer houseTypeId;//户型id
	
	private Integer seriesId;//系列id
	
	private String solutionName;//方案名称

	@ApiModelProperty("平面设计图")
	private String solutionGraphicDesignUrl;
	
	private String styleName;//风格名称
	
	private String headImgURL;//首图地址
	
	private List<String> allImages;//方案的所有图片
	
	private Integer itemCount;//家具数量
	
	private BigDecimal solutionTotalSalePrice;//方案整体售卖价
	
	private BigDecimal solutionDiscount;//方案整体折扣
	
	private BigDecimal solutionTotalDiscountPrice;//方案整体折扣价
	
	private Integer decorationType;// 装修类型：0硬装+软装，1纯软装
	
	private Integer zoneId;//分区ID

	private String solutionDesignIdea;//方案设计描述

	private String advantage;//方案优点
	
	private List<String> tagList;//方案标签

	@ApiModelProperty("是否为标准化硬装方案 0 不是 1 是")
	private Integer standardHardSolutionFlag = 1;

	@ApiModelProperty("方案状态 4是上架")
	private Integer solutionStatus = 4 ;

}
