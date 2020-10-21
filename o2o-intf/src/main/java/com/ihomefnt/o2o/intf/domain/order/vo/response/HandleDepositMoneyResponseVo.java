package com.ihomefnt.o2o.intf.domain.order.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单添加定金收款单返回")
public class HandleDepositMoneyResponseVo {

    @ApiModelProperty("业务收款单编号")
    private Integer billId;
}
