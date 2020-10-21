package com.ihomefnt.o2o.intf.domain.finalkeeper.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("确认收款单入参")
public class ConfirmFundRequest {

    @ApiModelProperty("实际收款金额")
    private BigDecimal actualAmount;

    @ApiModelProperty("实际入账时间")
    private String actualPayTime;

    @ApiModelProperty("收款银行账号")
    private Integer bankAccount;

    @ApiModelProperty("订单收款记录id")
    private Integer billId;

    @ApiModelProperty("退款时间")
    private String payTimeStr;

    @ApiModelProperty("退款方式")
    private Integer payType;

    @ApiModelProperty("付、退款备注")
    private String remark;

    @ApiModelProperty("操作人")
    private Integer userId;
}
