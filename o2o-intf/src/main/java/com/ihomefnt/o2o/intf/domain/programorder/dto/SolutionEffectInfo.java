package com.ihomefnt.o2o.intf.domain.programorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiamingyu
 * @date 2018/7/18
 */

@ApiModel("方案效果")
@Data
@Accessors(chain = true)
public class SolutionEffectInfo implements Serializable{

    @ApiModelProperty("方案id")
    private Integer solutionId;

    @ApiModelProperty("方案名称")
    private String solutionName;

    @ApiModelProperty("方案风格")
    private String solutionStyle;

    @ApiModelProperty("方案亮点")
    private String solutionAdvantage;

    @ApiModelProperty("方案头图")
    private String headImage;

    @ApiModelProperty("全屋方案原价")
    private Integer solutionPrice;

    @ApiModelProperty("空间设计列表")
    private List<SpaceDesign> spaceDesignList;

    @ApiModelProperty("是否定制")
    private Boolean customized;

    @ApiModelProperty("方案类型 0软装+硬装 1纯软装")
    private Integer decorationType;

    @ApiModelProperty("方案类型 1定制方案 2贷款方案 3本户型方案 4相似户型方案 默认相识户型")
    private Integer solutionType = 4;

    @ApiModelProperty("定价后的上线时间")
    private String publishTime;

    @ApiModelProperty("户型id")
    private Long apartmentId;

    @ApiModelProperty(value = "户型版本")
    private Long apartmentVersion;

    @ApiModelProperty("是否是拆改方案 0 不是 1 是")
    private Integer reformFlag;

    @ApiModelProperty("平面设计图")
    private String solutionGraphicDesignUrl;

    @ApiModelProperty("是否修改中 true是 false否")
    private Boolean isRevised = false;

}
