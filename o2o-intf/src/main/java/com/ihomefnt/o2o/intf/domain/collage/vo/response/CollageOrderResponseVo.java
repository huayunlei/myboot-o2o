package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * H5拼团下单响应
 * @author jerfan cang
 * @date 2018/10/15 17:05
 */
@Data
@ApiModel("CollageOrderResponseVo")
public class CollageOrderResponseVo {

    @ApiModelProperty("WechatResponse 支付信息")
    private WechatPayResponseVo weChatPayInfo;

    @ApiModelProperty(" 订单编号")
    private Integer orderId;

    @ApiModelProperty("服务器当前时间戳")
    private Long currentTime;

    public CollageOrderResponseVo(WechatPayResponseVo weChatPayInfo, Integer orderId) {
        this.weChatPayInfo = weChatPayInfo;
        this.orderId = orderId;
        this.currentTime = System.currentTimeMillis();
    }
}
