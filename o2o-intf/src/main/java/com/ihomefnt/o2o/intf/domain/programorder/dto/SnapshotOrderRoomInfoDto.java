package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author liguolin
 * @time 2017-06-17 14:26:06
 * @desc 快照空间信息
 */
@ApiModel("快照空间信息")
@Data
public class SnapshotOrderRoomInfoDto {

	/**
	 * 主键id
	 */
	@ApiModelProperty("主键id")
	private Long id;

	/**
	 * 大订单id
	 */
	@ApiModelProperty("大订单id")
    private Integer orderNum;

	/**
	 * 空间id
	 */
	@ApiModelProperty("空间id")
    private Integer roomId;

	/**
	 * 空间名称
	 */
	@ApiModelProperty("空间名称")
    private String roomName;

	/**
	 * 空间图片
	 */
	@ApiModelProperty("空间图片")
    private String roomImage;
	
	
	/**
	 * 空间售卖价
	 */
	@ApiModelProperty("空间售卖价")
	private BigDecimal roomSalePrice;
	
	
	/**
	 * 空间软装售卖价
	 */
	@ApiModelProperty("空间软装售卖价")
	private BigDecimal roomSoftSalePrice;
	
	/**
	 * 空间硬装售卖价
	 */
	@ApiModelProperty("空间硬装售卖价")
	private BigDecimal roomHardSalePrice;
	
	/**
	 * 方案id
	 */
	@ApiModelProperty("方案id")
    private Integer solutionId;

	/**
	 * 方案名称
	 */
	@ApiModelProperty("方案名称")
    private String solutionName;
	
	@ApiModelProperty("方案类型")
	private Integer decorationType;

	/**
	 * 套系名称
	 */
	@ApiModelProperty("套系名称")
    private String suitName;

	/**
	 * 设计风格
	 */
	@ApiModelProperty("设计风格")
    private String solutionStyleName;

	@ApiModelProperty("平面设计图")
	private String graphicDesignUrl;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
    private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
    private Date updateTime;

	/**
	 * 删除标识0：未删除1;已删除
	 */
	@ApiModelProperty("删除标识0：未删除1;已删除")
    private Byte delFlag;

	/**
	 * 硬装标准描述
	 */
	@ApiModelProperty("硬装标准描述")
    private String hardListDesc;
    
	/**
	 * 订单空间下sku信息
	 */
	@ApiModelProperty("订单空间下sku信息")
    private List<SnapshotOrderRoomSkuInfo> snapshotOrderRoomSkuInfos;
	
	
	/**
	 * 空间图片列表
	 */
	private List<SnapshotPictureInfoDto> pictureVoInfos;

	@ApiModelProperty("方案优点")
	private String solutionAdvantage;

	@ApiModelProperty("方案标签")
	private List<String> solutionTags;

}
