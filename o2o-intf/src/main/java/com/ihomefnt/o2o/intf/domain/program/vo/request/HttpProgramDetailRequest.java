package com.ihomefnt.o2o.intf.domain.program.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("产品方案详情请求参数")
@Data
public class HttpProgramDetailRequest extends HttpBaseRequest {
    @ApiModelProperty("方案ID")
    private Integer programId;

    @ApiModelProperty("订单ID")
    private Integer orderId;

    @ApiModelProperty("订单类型:11(套装、硬装+软装)、12(套装、纯软装)、13(自由搭配、硬装+软装)、14(自由搭配、纯软装)")
    private Integer orderType;

    @ApiModelProperty("空间id集合,查部分空间使用，查全部不需要")
    private List<Integer> roomIdList;

}
