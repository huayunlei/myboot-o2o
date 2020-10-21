package com.ihomefnt.o2o.intf.domain.style.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("已选问题答案查询结果")
public class StyleQuestionSelectedNewResponse {

    @ApiModelProperty("已选问题答案查询结果")
    private List<StyleQuestionSelectedResponse> styleQuestionSelectedResponseList;

    @ApiModelProperty("dolly设计任务ID")
    private Integer taskId;

    @ApiModelProperty("已选答案版本号")
    private Integer version;

}
