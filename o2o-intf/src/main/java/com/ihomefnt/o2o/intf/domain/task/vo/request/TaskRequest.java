package com.ihomefnt.o2o.intf.domain.task.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("催一催任务入参")
@Data
public class TaskRequest {

    @ApiModelProperty("订单编号")
    private Integer orderId;
}
