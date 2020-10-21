package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 艾久久消费明细
 */
@Data
public class AiJiuJiuRewardDetailDto {

    @ApiModelProperty("标准质保时长")
    private String expiryDate;

    @ApiModelProperty("质保开始时间")
    private Date expiryStartDate;

    @ApiModelProperty("质保到期时间")
    private Date expiryEndDate;

}
