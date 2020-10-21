package com.ihomefnt.o2o.intf.domain.homecard.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品中心服务用户特定方案返回对象
 * @author ZHAO
 */
@Data
public class SolutionInfoResponseVo {
	
	private Integer houseProjectId;//楼盘项目id
	
	private String houseProjectName;//楼盘项目名称
	
	private Integer houseTypeId;//户型id
	
	private String houseTypeName;//户型名称
	
	private List<SolutionIdAndHeadImgVo> solutionIdAndHeadImgList;//楼盘方案信息列表
	
	private Integer styleCount;//风格数量
	
	private Integer softDecorationStandardCount;//软装标准数量
	
	private BigDecimal minSolutionSalePrice;//方案最低价
	
	private BigDecimal maxSolutionSalePrice;//方案最高价
	
	private BigDecimal houseTypeArea;//户型面积

}
