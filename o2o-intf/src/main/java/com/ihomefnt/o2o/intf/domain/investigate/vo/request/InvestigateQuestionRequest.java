package com.ihomefnt.o2o.intf.domain.investigate.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/3 4:11 下午
 */
@Data
public class InvestigateQuestionRequest {

    @ApiModelProperty("问题id")
    private Integer id;

    @ApiModelProperty("问卷id")
    private Integer investigateId;

    @ApiModelProperty("编号")
    private Integer num;

    @ApiModelProperty("问题分类：个人信息、家庭情况、装修打算、更多信息")
    private String classify;

    @ApiModelProperty("问题题目")
    private String subject;

    @ApiModelProperty("问题类型：1填空，2单选，3多选")
    private Integer type;

    @ApiModelProperty("排序号")
    private Integer sortNo;

    @ApiModelProperty("是否必填 0非必填，1必填")
    private Integer isMust;

    @ApiModelProperty("答案集合")
    private List<InvestigateAnswerResquest> selectedAnswers;

}
