package com.ihomefnt.o2o.intf.domain.order.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("更新订单交房日期入参")
public class UpdateDeliverTimeRequestVo {

    @ApiModelProperty("交房日期")
    private String deliverTimeStr;

    @ApiModelProperty("预计交付日期")
    private String expectTimeStr;

    @ApiModelProperty("操作人ID")
    private Integer operatorId;

    @ApiModelProperty("订单ID")
    private Integer orderId;

}
