package com.ihomefnt.o2o.intf.domain.personalneed.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/24 10:41 上午
 */
@ApiModel("QueryDesignStatusResponse")
@Data
@Accessors(chain = true)
public class QueryDesignStatusResponse {

    @ApiModelProperty("订单Id")
    private Integer orderId;

    @ApiModelProperty("设计任务状态：1已提交 2设计中 3设计完成 -1已取消 ")
    private Integer taskStatus;

    @ApiModelProperty("设计任务状态名称")
    private String taskStatusName;
}
