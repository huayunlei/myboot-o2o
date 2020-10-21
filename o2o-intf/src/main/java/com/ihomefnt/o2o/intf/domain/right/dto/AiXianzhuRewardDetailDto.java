package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 艾先住消费明细
 */
@Data
public class AiXianzhuRewardDetailDto {

    @ApiModelProperty("交房时间")
    private Date deliverTime;

    @ApiModelProperty("开工时间")
    private Date beginDate;
}
