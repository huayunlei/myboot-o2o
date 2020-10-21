package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/17 16:11
 */
@Data
@ApiModel("CreateCollageOrderVo")
public class CreateCollageOrderVo {

    @ApiModelProperty("code")
    private Integer code;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("用户id")
    private Integer userId;
}
