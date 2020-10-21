package com.ihomefnt.o2o.intf.domain.delivery.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 最终验收
 */
@ApiModel
@Data
public class FinalCheckParamRequest extends HttpBaseRequest {

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("评论")
    private String comment;

    @ApiModelProperty("是否通过 0-不通过，1-通过")
    private Integer confirm;

}
