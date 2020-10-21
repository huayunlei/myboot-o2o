package com.ihomefnt.o2o.intf.domain.investigate.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/3 4:08 下午
 */
@Data
@ApiModel("InvestigateCommitRequest")
public class InvestigateCommitRequest extends HttpBaseRequest {

    @ApiModelProperty("提交渠道：1 APP，2 H5")
    private Integer channel;

    @ApiModelProperty("订单号")
    private Integer orderId;

    @ApiModelProperty("提交耗时，单位：秒")
    private Long consumeTime;

    @ApiModelProperty("问卷ID")
    private Integer investigateId;

    @ApiModelProperty("问卷名称")
    private String investigateName;

    @ApiModelProperty("验证码")
    private String authCode;

    @ApiModelProperty("手机号码")
    private String mobile; // 手机号码

    @ApiModelProperty("问题集合")
    private List<InvestigateQuestionRequest> selectedQuestions;

}
