package com.ihomefnt.o2o.intf.domain.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单号返回
 *
 * @author liyonggang
 * @create 2019-03-07 10:17
 */
@Data
@ApiModel("订单号返回")
@Accessors(chain = true)
public class OrderIdResponse {

    @ApiModelProperty("订单id")
    private Integer orderId;

}
