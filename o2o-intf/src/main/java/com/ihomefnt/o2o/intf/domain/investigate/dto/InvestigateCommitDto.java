package com.ihomefnt.o2o.intf.domain.investigate.dto;

import com.ihomefnt.o2o.intf.domain.investigate.vo.request.InvestigateQuestionRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/3 4:08 下午
 */
@Data
@Accessors(chain = true)
public class InvestigateCommitDto {

    @ApiModelProperty("提交渠道：1 APP，2 H5")
    private Integer channel;

    @ApiModelProperty("订单号")
    private Integer orderId;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("提交人")
    private String submitter;

    @ApiModelProperty("订单状态，12:待交付 13：接触阶段，14：意向阶段，15：定金阶段，16：签约阶段，17：交付中，2：已完成，3：已取消")
    private Integer orderStatus;

    @ApiModelProperty("提交耗时，单位：秒")
    private Long consumeTime;

    @ApiModelProperty("问卷ID")
    private Integer investigateId;

    @ApiModelProperty("问卷名称")
    private String investigateName;

    @ApiModelProperty("问题集合")
    private List<InvestigateQuestionRequest> selectedQuestions;

}
