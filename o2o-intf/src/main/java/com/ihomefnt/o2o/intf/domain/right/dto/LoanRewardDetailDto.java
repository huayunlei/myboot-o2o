package com.ihomefnt.o2o.intf.domain.right.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 全品家立减消费明细
 */
@Data
public class LoanRewardDetailDto {

    @ApiModelProperty("银行名称")
    private String bankName;
    @ApiModelProperty("银行卡号")
    private String banbankCardNumberkName;
    @ApiModelProperty("已到账金额")
    private BigDecimal refundedAmount;
    @ApiModelProperty("在途金额")
    private BigDecimal refundingAmount;
}
