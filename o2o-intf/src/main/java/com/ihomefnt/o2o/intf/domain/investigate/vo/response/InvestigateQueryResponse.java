package com.ihomefnt.o2o.intf.domain.investigate.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/3 4:08 下午
 */
@Data
public class InvestigateQueryResponse {

    @ApiModelProperty("问卷ID")
    private Integer id;

    @ApiModelProperty("问卷名称")
    private String name;

    @ApiModelProperty("问卷描述信息")
    private String remark;

    @ApiModelProperty("问题集合")
    private List<InvestigateQuestionResponse> questionList;

}
