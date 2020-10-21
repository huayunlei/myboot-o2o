package com.ihomefnt.o2o.intf.domain.homepage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiamingyu
 * @date 2018/7/18
 */
@Data
@ApiModel("选方案草稿返回")
public class SolutionDraftResponse {

    @ApiModelProperty("草稿id")
    private String draftId;

    @ApiModelProperty("草稿内容")
    private String draftJsonStr;

    @ApiModelProperty("选方案进度")
    private BigDecimal draftProgress;

    private Integer draftType;
}
