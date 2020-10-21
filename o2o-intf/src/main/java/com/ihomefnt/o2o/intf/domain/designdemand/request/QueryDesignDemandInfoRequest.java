package com.ihomefnt.o2o.intf.domain.designdemand.request;

import com.ihomefnt.o2o.intf.domain.style.vo.request.QuerySelectedQusAnsRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询设计任务详情")
public class QueryDesignDemandInfoRequest extends QuerySelectedQusAnsRequest {

    @ApiModelProperty("设计任务草稿ID")
    private String designDemandId;

    @ApiModelProperty("用户ID")
    private Integer userId;

}
