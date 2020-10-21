package com.ihomefnt.o2o.intf.domain.program.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BatchQuerySolutionByHouseIdRequestVo {

    @ApiModelProperty("户型ID")
    private Integer apartmentId;

    @ApiModelProperty("删除状态 0 未删除")
    private Integer delFlag=0;

    @ApiModelProperty("方案状态 4 在线")
    private Integer solutionStatus=4;

    @ApiModelProperty("查询限制数量")
    private Integer limit=10;
}
