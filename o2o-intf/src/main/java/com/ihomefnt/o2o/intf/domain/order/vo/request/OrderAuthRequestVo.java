package com.ihomefnt.o2o.intf.domain.order.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author hua
 * @Date 2020/1/13 10:00 上午
 */
@Data
@Accessors(chain = true)
@ApiModel("OrderAuthRequest 请求参数")
public class OrderAuthRequestVo {

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("请求接口")
    private String requestURI;
}
