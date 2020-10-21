package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class RuleDto {

    @ApiModelProperty("等级")
    private Integer gradeId;

    @ApiModelProperty("等级名称")
    private String gradeName;

    @ApiModelProperty("时限")
    private Integer gradeTimeLimit;

    @ApiModelProperty("所需金额")
    private BigDecimal gradeAmount;

    @ApiModelProperty("权益等级APP显示名称")
    private String gradeNameCopywriting;
}
