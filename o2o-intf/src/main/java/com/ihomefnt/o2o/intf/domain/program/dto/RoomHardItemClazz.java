package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 响应给app
 * @author jerfan cang
 * @date 2018/10/7 20:14
 */
@Data
public class RoomHardItemClazz {

    @ApiModelProperty("选配类别id")
    private Integer hardItemClassId;

    @ApiModelProperty("选配类别名称")
    private String hardItemClassName;

    @ApiModelProperty("选配类别示意图")
    private String hardItemClassImage;

    @ApiModelProperty("选配项目描述")
    private String hardItemClassDesc;

    @ApiModelProperty("硬装sku列表")
    private List<RoomHardItem> hardItemClassList;

    @ApiModelProperty("是否包含默认sku")
    private Boolean contain;

    /*
     * 硬装二期全屋空间需求
     * add by cangjifeng
     * date 2018-09-30
     * 硬装包下面挂sku
     */
    @ApiModelProperty("硬装商品包列表（全屋下有）")
    private List<RoomHardPackage> hardPackageList;

}
