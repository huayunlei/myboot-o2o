package com.ihomefnt.o2o.intf.domain.designdemand.response;

import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionSelectedResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("查询小艾提交设计任务详情")
public class QueryDesignDemandInfoResponse {

    @ApiModelProperty("问答详情")
    List<StyleQuestionSelectedResponse> styleQuestionList;

    @ApiModelProperty("验证码手机号")
    String mobileNum;

    @ApiModelProperty("设计任务状态 111待提交 112待确认 其它：小艾不可编辑")
    Integer taskStatus;

    @ApiModelProperty("设计任务状态描述")
    String taskStatusStr;

    @ApiModelProperty("创建人id")
    private Integer createUserId;

    @ApiModelProperty("最新设计任务是否设计中")
    private Boolean lastTaskIsUnderDesign = Boolean.FALSE;
}
