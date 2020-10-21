package com.ihomefnt.o2o.intf.domain.investigate.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/3 4:12 下午
 */

@Data
public class InvestigateAnswerResponse {

    @ApiModelProperty("答案id")
    private Integer id;

    @ApiModelProperty("问题id")
    private Integer questionId;

    @ApiModelProperty("问题答案")
    private String answer;

    @ApiModelProperty("问题类型为选择，且答案类型为填空时有值：其他")
    private String otherAnswer;

    @ApiModelProperty("答案类型：1填空，2选择")
    private Integer type;

    @ApiModelProperty("排序号")
    private Integer sortNo;

}
