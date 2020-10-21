package com.ihomefnt.o2o.intf.domain.demo.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("演示系统通用入参")
public class CompleteDesignDemandRequest extends DemoCommonRequest {
    @ApiModelProperty("订单号")
    private Integer solutionId;

}
