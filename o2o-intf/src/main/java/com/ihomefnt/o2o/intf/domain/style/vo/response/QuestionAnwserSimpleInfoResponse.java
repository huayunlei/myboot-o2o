package com.ihomefnt.o2o.intf.domain.style.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设计任务信息
 *
 * @author liyonggang
 * @create 2019-03-29 20:31
 */
@Data
@ApiModel("设计任务信息")
@Accessors(chain = true)
public class QuestionAnwserSimpleInfoResponse {

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("任务状态")
    private Integer taskStatus;

    @ApiModelProperty("任务状态描述")
    private String taskStatusStr;

    @ApiModelProperty("方案ID")
    private Integer solutionId;

    @ApiModelProperty("是否需要做特殊提醒 0否 1是")
    private Integer specialFlag = 0;//为了更好地了解您的需求，我们更新了问卷中部分问题，因此修改设计需求时有些问题您可能需要重新回答。

    @ApiModelProperty("特殊提醒 显示文案")
    private String specialMessage;

    @ApiModelProperty("导读页是否已读")
    private Boolean solutionsHasRead = true;

    @ApiModelProperty("是否已经确认方案")
    private Integer isConfirmed = 0;

    @ApiModelProperty("是否交清全款 0否 1是 2超过应付金额")
    private Integer allMoney = 0;

    private Boolean lastTaskIsUnderDesign = Boolean.FALSE;
}
