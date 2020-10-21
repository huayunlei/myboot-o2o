package com.ihomefnt.o2o.intf.domain.promotion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by danni.wu on 2018/5/25.
 */
@Data
@ApiModel(value = "订单参加促销活动优惠详情")
public class PromotionRewardDetailDto {

    @ApiModelProperty(value="累积已返息金额")
    private BigDecimal sumReturnInterest = BigDecimal.ZERO;

    @ApiModelProperty(value="累积已返现金额")
    private BigDecimal sumReturnCash = BigDecimal.ZERO;

    @ApiModelProperty(value="累积已返收益金额")
    private BigDecimal sumReturnProfit = BigDecimal.ZERO;

    @ApiModelProperty(value="累积已返艾积分")
    private BigDecimal sumReturnAipoints = BigDecimal.ZERO;

    @ApiModelProperty(value="累积待返息金额")
    private BigDecimal sumWaitingInterest = BigDecimal.ZERO;

    @ApiModelProperty(value="累积待返现金额")
    private BigDecimal sumWaitingCash = BigDecimal.ZERO;

    @ApiModelProperty(value="累积待返收益金额")
    private BigDecimal sumWaitingProfit = BigDecimal.ZERO;

    @ApiModelProperty(value="累积待返艾积分")
    private BigDecimal sumWaitingAipoints = BigDecimal.ZERO;
}
