package com.ihomefnt.o2o.intf.domain.vote.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanyunxin
 * @create 2019-10-18 17:13
 */
@NoArgsConstructor
@Data
public class QuestionnaireResultDto {

    @ApiModelProperty("性格色彩标签id")
    private Integer lableId;

    @ApiModelProperty("性格色彩标签名称")
    private String labelDetail;

    @ApiModelProperty("性格色彩标签占比")
    private Double labelRate;
}
