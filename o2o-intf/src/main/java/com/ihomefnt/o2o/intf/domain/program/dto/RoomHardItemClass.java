/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: 闫辛未
 * Date: 2018/7/24
 * Description:RoomHardItem.java
 */
package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.program.vo.response.HardBomGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 闫辛未
 */
@ApiModel("空间硬装选配类型")
@Data
public class RoomHardItemClass implements Serializable {
    @ApiModelProperty("选配类别id")
    private Integer hardItemClassId;

    @ApiModelProperty("选配类别名称")
    private String hardItemClassName;

    @ApiModelProperty("选配类别示意图")
    private String hardItemClassImage;

    @ApiModelProperty("选配项目描述")
    private String hardItemClassDesc;

    @ApiModelProperty("唯一标识")
    private String superKey;

    @ApiModelProperty("硬装sku列表")
    private List<RoomHardItem> hardItemClassList;

    @ApiModelProperty("是否可以勾除 true 不可 false 可")
    private Boolean contain;

    /*
     * 硬装二期全屋空间需求
     * add by cangjifeng
     * date 2018-09-30
     * 硬装包下面挂sku
     */
    @ApiModelProperty("硬装商品包列表（全屋下有）")
    private List<RoomHardPackageVo> hardPackageList;

    @ApiModelProperty("是否有替换项")
    private Boolean hasReplaceItem;

    @ApiModelProperty("是否是标配 0否 1是")
    private Integer isStandardItem;

    @ApiModelProperty("类型 1：硬装，2：bom硬装")
    private Integer hardItemType;

    @ApiModelProperty("硬装bom组合信息")
    private HardBomGroup bomGroup;

    @ApiModelProperty("组合替换列表 方案详情使用")
    private ReplaceBomGroupListDto replaceBomGroupList;

    @ApiModelProperty("末级类目id")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;

    @Data
    public static class ReplaceBomGroupListDto {
        private Integer totalRecords;
        private Integer pageSize;
        private Integer pageNo;
        private Integer totalPages;
        @ApiModelProperty("替换项列表")
        private List<HardBomGroup> list;
    }
}
