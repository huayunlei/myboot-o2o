package com.ihomefnt.o2o.intf.domain.programorder.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("快速完成设计任务入参")
public class DirectCompleteSolutionTaskRequestVo {

    @ApiModelProperty("匹配设计任务")
    private Integer solutionId;

    @ApiModelProperty("设计任务id")
    private Integer taskId;
}
