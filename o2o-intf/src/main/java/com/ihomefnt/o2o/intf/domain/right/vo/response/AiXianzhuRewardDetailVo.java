package com.ihomefnt.o2o.intf.domain.right.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 艾先住消费明细
 */
@ApiModel("AiXianzhuRewardDetailVo")
@Data
public class AiXianzhuRewardDetailVo {

    @ApiModelProperty("交房时间")
    private Date deliverTime;

    @ApiModelProperty("开工时间")
    private Date beginDate;
    
    @ApiModelProperty("交房时间")
    private String deliverTimeStr;

    @ApiModelProperty("开工时间")
    private String beginDateStr;
}
