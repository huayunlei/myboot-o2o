package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户权益版本信息
 */
@Data
public class GradeVersionDto {

    @ApiModelProperty("等级")
    private Integer gradeId;

    @ApiModelProperty("权益版本")
    private Integer version;

}
