package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/17 19:33
 */
@Data
@ApiModel("CancelCollageOrderResponseVo")
public class CancelCollageOrderResponseVo {

    @ApiModelProperty("取消订单状态 true 取消成功 false 取消失败")
    private boolean cancelMark;

    @ApiModelProperty("订单id")
    private Integer orderId;

    public CancelCollageOrderResponseVo(Integer orderId,boolean cancelMark) {
        this.orderId = orderId;
        this.cancelMark = cancelMark;
    }
}