package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.HouseLayoutVo;
import com.ihomefnt.o2o.intf.domain.program.dto.NoStandardUpgradeInfoVo;
import com.ihomefnt.o2o.intf.domain.program.dto.SolutionRoomDetailVo;
import com.ihomefnt.o2o.intf.domain.program.dto.StandardUpgradeInfoVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 根据大订单id查询自由搭配方案详情
 * @author ZHAO
 */
@Data
public class AladdinSolutionDetailResponseVo {
	private Integer solutionId;// 方案id
	
	private String solutionName;//方案名称
	
	private String solutionHeadImgURL;//方案首图地址
	
	private Integer houseProjectId;//楼盘id
	
	private String houseProjectName;//楼盘名称
	
	private Integer houseTypeId;//户型id
	
	private String houseTypeName;//户型名称
	
	private HouseLayoutVo houseLayout;//户型信息
	
	private String solutionSeriesName;//系列名称
	
	private String solutionStyleName;//风格名称
	
	private BigDecimal solutionDiscount;//方案折扣
	
	private String solutionGraphicDesignUrl;//平面设计图
	
	private Integer decorationType;//装修类型：0硬装+软装，1纯软装
	
	private String solutionDesignIdea;//方案设计描述
	
	private BigDecimal solutionTotalSalePrice;//方案总售卖价
	
	private BigDecimal solutionTotalDiscountPrice;//方案折扣后总价
	
	private BigDecimal solutionTotalHardDecorationSalePrice;//方案软装总售卖价
	
	private BigDecimal solutionTotalSoftDecorationDiscountPrice;//方案硬装总售卖价
	
	private Integer solutionTotalItemCount;//方案家具总数量
	
	private List<SolutionRoomDetailVo> solutionRoomDetailVoList;//方案空间列表
	
	private Boolean isAutoMatch;//是否自由搭配

	private List<AladdinAddBagInfoVo> addBags;//增配包商品列表
	
	private List<StandardUpgradeInfoVo> upgradeInfos;//标准升级包信息
	
	private List<NoStandardUpgradeInfoVo> noUpgradeInfos;//非标准升级包信息
	
	private String unitNum;//单元号
	
	private Integer zoneId;//分区id
	
	private String partitionName;//分区名称
	
	private String solutionAdvantage;//方案优点
	
	private List<String> solutionTags;//方案标签
	
}
