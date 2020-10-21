package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 权益查询返回内容
 */
@Data
public class GradeClassifyDto {

    @ApiModelProperty("等级")
    private Integer gradeId;

    @ApiModelProperty("等级名称")
    private String gradeName;

    @ApiModelProperty("权益等级图片")
    private String gradeNameUrl;

    @ApiModelProperty("权益等级背景图")
    private String gradeBackGround;

    @ApiModelProperty("权益分类")
    private List<RightsClassifyDto> classifyDtoList;

    @ApiModelProperty("等级规则列表")
    private List<RuleDto> rulesDtos;

    @ApiModelProperty("权益等级")
    private String gradeNameCopywriting;

    @ApiModelProperty("权益内容总计")
    private String gradeClassifyCountDesc;

    @ApiModelProperty("权益描述")
    private String gradeClassifyDeac;

}
