package com.ihomefnt.o2o.intf.domain.collage.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jerfan cang
 * @date 2018/10/17 19:30
 */
@Data
@NoArgsConstructor
@ApiModel("CancelCollageOrderRequest")
public class CancelCollageOrderRequest {

    @ApiModelProperty("openId")
    private String openid;

    @ApiModelProperty("活动id")
    private Integer activityId;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("手机号 -收件人手机号")
    private String receiveTel;

    public CancelCollageOrderRequest(String openid, Integer activityId, Integer orderId, String receiveTel) {
        this.openid = openid;
        this.activityId = activityId;
        this.orderId = orderId;
        this.receiveTel = receiveTel;
    }
}