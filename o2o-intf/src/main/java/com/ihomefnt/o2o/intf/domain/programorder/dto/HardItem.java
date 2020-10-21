package com.ihomefnt.o2o.intf.domain.programorder.dto;

import com.ihomefnt.o2o.intf.domain.program.dto.RoomHardPackageVo;
import com.ihomefnt.o2o.intf.domain.program.vo.request.QueryCabinetPropertyListRequest;
import com.ihomefnt.o2o.intf.domain.program.vo.response.HardBomGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/19
 */

@ApiModel("硬装选配项目")
@Data
public class HardItem implements Serializable {

    @ApiModelProperty("是否默认包含")
    private Boolean contain;

    @ApiModelProperty("选配项目id")
    private Integer hardItemId;

    @ApiModelProperty("选配项目名称")
    private String hardItemName;

    @ApiModelProperty("唯一标识")
    private String superKey;

    @ApiModelProperty("选配项目示意图")
    private String hardItemImage;

    @ApiModelProperty("选配项目描述")
    private String hardItemDesc;

    @ApiModelProperty("选配包列表")
    private List<HardItemSelection> hardItemSelectionList;

    //草稿用、清单用
    @ApiModelProperty("已选选配包")
    private HardItemSelection hardItemSelected;

    @ApiModelProperty("默认选配包")
    private HardItemSelection hardItemDefault;

    //草稿用、清单用
    @ApiModelProperty("已选全屋替换包")
    private RoomHardPackageVo hardPackageSelected;

    @ApiModelProperty("默认全屋替换包")
    private RoomHardPackageVo hardPackageDefault;

    @ApiModelProperty("全屋下的选配包列表")
    private List<RoomHardPackageVo> hardPackageList;

    @ApiModelProperty("1:bom组合 0:普通商品 102定制柜")
    private Integer bomFlag = 0;

    @ApiModelProperty("定制柜数据,全品家清单使用")
    private List<HardBomGroup> guiBomGroupList;

    @ApiModelProperty("查询详情使用参数")
    private List<QueryCabinetPropertyListRequest.GroupQueryRequest> guiBomGroupQueryList;

    @ApiModelProperty("定制柜数据 草稿使用")
    private CabinetBomDto cabinetBomGroup;
}
