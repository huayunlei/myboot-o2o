package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class DemandConfirmationResponseVo {
    private boolean demandResult;
    private String scheduledDate;
    @ApiModelProperty("保价优惠金额")
    private BigDecimal preferentialAmount;

    @ApiModelProperty("订单总价信息")
    private BigDecimal finalOrderPrice;
}
