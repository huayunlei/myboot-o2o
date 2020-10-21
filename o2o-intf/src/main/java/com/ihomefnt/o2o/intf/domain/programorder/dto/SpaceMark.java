package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/18
 */

@ApiModel("空间标识")
@Data
public class SpaceMark implements Serializable{

    @ApiModelProperty("空间id")
    private Integer roomId;

    @ApiModelProperty("空间效果列表")
    private List<SpaceDesign> spaceDesignList;

    @ApiModelProperty("已选空间")
    private SpaceDesign spaceEffectSelected;

    @ApiModelProperty("默认空间")
    private SpaceDesign spaceEffectDefault;
}
