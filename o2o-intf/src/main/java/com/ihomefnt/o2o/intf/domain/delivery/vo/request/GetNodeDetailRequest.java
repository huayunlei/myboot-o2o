package com.ihomefnt.o2o.intf.domain.delivery.vo.request;

import com.ihomefnt.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 最终验收
 */
@ApiModel("节点信息")
@Data
public class GetNodeDetailRequest extends HttpBaseRequest {

    @ApiModelProperty("订单id")
    @NotNull(message = "订单编号不能为空")
    private Integer orderId;

    @ApiModelProperty("节点id")
    @NotBlank(message = "节点id不能为空")
    private String nodeId;

}
