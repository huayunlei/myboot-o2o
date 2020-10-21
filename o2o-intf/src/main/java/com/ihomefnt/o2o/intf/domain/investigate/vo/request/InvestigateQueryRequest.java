package com.ihomefnt.o2o.intf.domain.investigate.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author hua
 * @Date 2020/3/3 4:05 下午
 */
@Data
@ApiModel("问卷查询request")
public class InvestigateQueryRequest {

    @ApiModelProperty("问卷ID")
    private Integer investigateId;
}
