package com.ihomefnt.o2o.intf.domain.right.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 艾久久消费明细
 */
@ApiModel("AiJiuJiuRewardDetailVo")
@Data
public class AiJiuJiuRewardDetailVo {

    @ApiModelProperty("标准质保时长")
    private String expiryDate;

    @ApiModelProperty("质保开始时间")
    private Date expiryStartDate;

    @ApiModelProperty("质保到期时间")
    private Date expiryEndDate;
    
    @ApiModelProperty("质保开始时间")
    private String expiryStartDateStr;

    @ApiModelProperty("质保到期时间")
    private String expiryEndDateStr;

}
