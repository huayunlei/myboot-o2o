package com.ihomefnt.o2o.intf.domain.order.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("订单添加定金收款单入参")
public class HandleDepositMoneyRequestVo {

    @ApiModelProperty("鉴权操作人")
    Integer operatorId;

    @ApiModelProperty("操作人")
    Integer userId;

    @ApiModelProperty("订单号")
    Integer orderNum;

    @ApiModelProperty("收款方式 51网划")
    Integer payType;

    @ApiModelProperty("备注")
    String remark;

    @ApiModelProperty("收款金额")
    BigDecimal transtionAmount;

}
