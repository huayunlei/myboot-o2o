package com.ihomefnt.o2o.intf.domain.task.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("催一催任务返回")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回附属信息")
    private String subMessage;
}
