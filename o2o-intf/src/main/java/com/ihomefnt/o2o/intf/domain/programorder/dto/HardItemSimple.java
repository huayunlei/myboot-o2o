package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.RoomHardPackageVo;
import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.HardBomGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-03-01 17:16
 */

@ApiModel("硬装选配项目简单信息")
@Data
public class HardItemSimple {

    @ApiModelProperty("是否可以勾除 true 不可 false 可")
    private Boolean contain;

    @ApiModelProperty("选配项目id")
    private Integer hardItemId;

    @ApiModelProperty("选配项目名称")
    private String hardItemName;

    @ApiModelProperty("选配项目示意图")
    private String hardItemImage;

    @ApiModelProperty("选配项目描述")
    private String hardItemDesc;

    @ApiModelProperty("选配包列表")
    private List<HardItemSimpleSelection> hardItemSelectionList;

    //草稿用、清单用
    @ApiModelProperty("已选选配包")
    private HardItemSelection hardItemSelected;

    @ApiModelProperty("默认选配包")
    private HardItemSelection hardItemDefault;

    @ApiModelProperty("全屋下的选配包列表")
    private List<RoomHardPackageVo> hardPackageList;

    private String superKey;

    @ApiModelProperty("是否有替换项")
    private Boolean hasReplaceItem;

    @ApiModelProperty("选配类别id")
    private Integer hardItemClassId;

    @ApiModelProperty("末级类目id")
    private Integer lastCategoryId;

    @ApiModelProperty("末级类目名称")
    private String lastCategoryName;

    @ApiModelProperty("是否是标配 0否 1是")
    private Integer isStandardItem;

    @ApiModelProperty("类型 1：硬装，2：bom硬装")
    private Integer hardItemType;

    @ApiModelProperty("硬装bom组合信息")
    private HardBomGroup bomGroup;

    @ApiModelProperty("定制柜数据")
    private CabinetBomDto cabinetBomGroup;


    @ApiModelProperty("查询详情使用参数")
    private List<QueryCabinetPropertyListRequest.GroupQueryRequest> guiBomGroupQueryList;

    @ApiModelProperty("是否支持可视化云渲染")
    private boolean supportDrawCategory = false;

    @ApiModelProperty("是否支持筛选")
    private Boolean supportFiltrate = Boolean.FALSE;

}
