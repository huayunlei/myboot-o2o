package com.ihomefnt.o2o.intf.domain.pay.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/28 12:38
 */
@Data
@ApiModel("WechatResponse")
public class WechatResponseVo {

    //商家根据财付通文档填写的数据和签名
    @ApiModelProperty("商家根据财付通文档填写的数据和签名")
    private String packagestr;

    // 应用ID
    @ApiModelProperty("应用ID")
    private String appid;

    @ApiModelProperty("签名 sign")
    private String sign;

    //商家申请的商家id
    @ApiModelProperty("ApiModelProperty")
    private String partnerid;

    //生成预支付订单的订单id
    @ApiModelProperty("生成预支付订单的订单id")
    private  String prepayid;

    //随机串，防重发
    @ApiModelProperty("随机串，防重发")
    private  String noncestr;

    //时间戳，防重发
    @ApiModelProperty("时间戳，防重发")
    private  String timestamp;

}
