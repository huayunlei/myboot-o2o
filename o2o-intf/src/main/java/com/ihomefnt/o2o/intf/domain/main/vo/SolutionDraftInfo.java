package com.ihomefnt.o2o.intf.domain.main.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiamingyu
 * @date 2019/3/19
 */

@ApiModel("方案草稿信息")
@Data
@Accessors(chain = true)
public class SolutionDraftInfo {

    @ApiModelProperty("最新草稿Id")
    private String draftId;

    @ApiModelProperty("最新草稿方案头图")
    private String headImage;

    @ApiModelProperty("草稿数量")
    private Integer draftCount;

    @ApiModelProperty("全屋方案原价")
    private Integer solutionPrice;

    @ApiModelProperty("方案类型:0软+硬，1纯软")
    private Integer solutionType;

    @ApiModelProperty("方案类型字符串")
    private String solutionTypeStr;

    @ApiModelProperty("方案风格名称")
    private String styleName;

    @ApiModelProperty("是否有新的渲染任务完成")
    private boolean hasNewDrawTaskFinish;

    @ApiModelProperty("状态 // 1已签约；2未签约；3历史签约 4.最新草稿")
    private Integer draftSignStatus;

    @ApiModelProperty
    private Long draftProfileNum;
}
