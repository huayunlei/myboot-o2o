package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.homebuild.dto.HouseLayoutVo;
import com.ihomefnt.o2o.intf.domain.program.vo.response.UserInfoResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 方案详情
 * 
 * @author ZHAO
 */
@Data
@ApiModel("SolutionDetailResponseVo")
public class SolutionDetailResponseVo implements Serializable {

	@ApiModelProperty("用户信息")
	private UserInfoResponse userInfo;// 用户信息

	@ApiModelProperty("方案id")
	private Integer solutionId;// 方案id

	@ApiModelProperty("方案名称")
	private String solutionName;// 方案名称

	@ApiModelProperty("方案首图地址")
	private String solutionHeadImgURL;// 方案首图地址

	@ApiModelProperty("楼盘id")
	private Integer houseProjectId;// 楼盘id

	@ApiModelProperty("楼盘名称")
	private String houseProjectName;// 楼盘名称

	@ApiModelProperty("分区ID")
	private Integer zoneId;//分区ID

	@ApiModelProperty("分区名称")
	private String zoneName;//分区名称

	/**
	 * @deprecated 用 apartmentId
	 */
	@ApiModelProperty("户型id")
	@Deprecated
	private Integer houseTypeId;// 户型id

	@ApiModelProperty("户型id")
	private Long apartmentId;

	@ApiModelProperty("户型版本")
	private Long apartmentVersion;

	@ApiModelProperty("户型格局")
	private String apartmentPattern;

	@ApiModelProperty("是否是拆改方案 0 不是 1 是")
	private Integer reformFlag;

	@ApiModelProperty("户型名称")
	private String houseTypeName;// 户型名称

	@ApiModelProperty("原始户型信息")
	private HouseLayoutVo houseLayout;//户型信息

	@ApiModelProperty("拆改后户型信息")
	private HouseLayoutVo reformApartment;

	@ApiModelProperty("系列名称")
	private String solutionSeriesName;// 系列名称

	@ApiModelProperty("风格名称")
	private String solutionStyleName;// 风格名称

	@ApiModelProperty("方案折扣")
	private BigDecimal solutionDiscount;// 方案折扣

	@ApiModelProperty("方案折扣")
	private String solutionDiscountStr;// 方案折扣

	@ApiModelProperty("平面设计图")
	private String solutionGraphicDesignUrl;//平面设计图

	@ApiModelProperty("装修类型 0硬装+软装  1纯软装")
	private Integer decorationType;//装修类型 0硬装+软装  1纯软装

	@ApiModelProperty("方案设计描述")
	private String solutionDesignIdea;//方案设计描述

	@ApiModelProperty("方案总售卖价")
	private BigDecimal solutionTotalSalePrice;// 方案总售卖价

	@ApiModelProperty("方案折扣后总价")
	private BigDecimal solutionTotalDiscountPrice;//方案折扣后总价

	@ApiModelProperty("方案软装总售卖价")
	private BigDecimal solutionTotalHardDecorationSalePrice;// 方案软装总售卖价

	@ApiModelProperty("方案硬装总售卖价")
	private BigDecimal solutionTotalSoftDecorationDiscountPrice;// 方案硬装总售卖价

	@ApiModelProperty("方案家具总数量")
	private Integer solutionTotalItemCount;// 方案家具总数量

	@ApiModelProperty("SolutionRoomDetailVo 方案空间列表")
	private List<SolutionRoomDetailVo> solutionRoomDetailVoList;// 方案空间列表

	@ApiModelProperty("方案全景地址")
	private String solutionGlobalViewURL;//方案全景地址

	@ApiModelProperty("SolutionAddBagInfoVo 方案增配包商品列表")
	private List<SolutionAddBagInfoVo> solutionExtraItemList;//方案增配包商品列表

	@ApiModelProperty("SolutionStandardUpgradesResponseVo 标准升级项")
	private SolutionStandardUpgradesResponseVo solutionStandardUpgradesResponseVo;// 标准升级项

	@ApiModelProperty("方案优点")
	private String advantage;//方案优点

	@ApiModelProperty("方案标签")
	private List<String> tagList;//方案标签

	private Integer solutionStatus;

	@ApiModelProperty("方案视频")
	private String videoUrl;
}
