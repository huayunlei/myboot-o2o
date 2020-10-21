package com.ihomefnt.o2o.intf.domain.style.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("风格问题答案对象")
@Data
public class StyleQuestionAnwserCommitNewRequest {

    @ApiModelProperty("dolly设计任务ID")
    Integer taskId;

    private Integer version;//设计需求版本号

    @ApiModelProperty("风格问题答案对象")
    List<StyleQuestionAnwserCommitRequest> styleQuestionAnwserCommitRequestList;

}
