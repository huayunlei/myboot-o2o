package com.ihomefnt.o2o.intf.domain.program.dto;

import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BomGroupVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 方案空间
 *
 * @author ZHAO
 */
@Data
public class SolutionRoomDetailVo implements Serializable {

    private Integer roomId;//空间id

    private String solutionName;//方案

    private String solutionSeriesName;//系列

    private String solutionStyleName;//风格

    private Integer roomUsageId;//空间功能名称

    private String roomUsageName;//空间功能名称

    private String roomDesc;//空间描述

    private String roomHeadImgURL;

    private BigDecimal roomSalePrice;//空间售卖价（设计师所定）

    private BigDecimal roomPrice;//空间价格

    private BigDecimal roomSoftDecorationSalePrice;// 空间软装售卖价

    private BigDecimal roomHardDecorationSalePrice;//空间硬装售卖价

    private List<SolutionRoomPicVo> solutionRoomPicVoList;//空间图片列表

    private List<SolutionRoomPicVo> solutionRoomViewPicVoList;//空间效果图列表

    private Integer roomItemCount;//空间家具数量

    private List<SolutionRoomItemVo> solutionRoomItemVoList = Lists.newArrayList();//空间家具列表

    private Integer styleId;//风格id

    private String styleName;//风格名称

    private Integer seriesId;//系列id

    private String seriesName;//系列名称

    private Integer subSkuCount;//可替换家具数量

    private List<SolutionReplaceRoomVo> replaceRoomSketchList;//可选空间概要信息

    @ApiModelProperty("空间默认硬装sku信息")
    private List<RoomDefaultHardItemClass> defaultHardItemList = Lists.newArrayList();

    @ApiModelProperty("组合信息")
    private List<BomGroupVO> bomGroupList = Lists.newArrayList();

    @ApiModelProperty("空间硬装组合信息")
    private List<BomGroupVO> hardBomGroupList = Lists.newArrayList();
    private Integer dnaId;
    private String dnaName;

}
