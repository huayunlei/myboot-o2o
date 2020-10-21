package com.ihomefnt.o2o.intf.domain.demo.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("演示系统通用入参")
public class DemoCommonRequest extends HttpBaseRequest {

    @ApiModelProperty("订单号")
    private Integer orderId;

    @ApiModelProperty("任务编号")
    private Integer taskId;

    @ApiModelProperty("交房日期")
    private Integer deliverTimeDiff;
}
