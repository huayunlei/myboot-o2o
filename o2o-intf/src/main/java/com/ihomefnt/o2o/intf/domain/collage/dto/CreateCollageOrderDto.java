package com.ihomefnt.o2o.intf.domain.collage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/17 16:10
 */
@Data
@ApiModel("CreateCollageOrderDto")
public class CreateCollageOrderDto {

    @ApiModelProperty("code")
    private Integer code;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("用户id")
    private Integer userId;
}
