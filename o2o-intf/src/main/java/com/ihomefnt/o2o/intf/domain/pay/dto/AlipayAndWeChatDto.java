package com.ihomefnt.o2o.intf.domain.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/9/25 16:43
 */
@Data
@ApiModel("支付宝和微信响应参数bean")
public class AlipayAndWeChatDto {

    @ApiModelProperty(value = "应用ID", notes = "由用户微信号和AppID组成的唯一标识，发送请求时第三方程序必须填写，用于校验微信用户是否换号登录")
    private String appid;

    @ApiModelProperty("商家申请的商家id")
    private String partnerid;

    @ApiModelProperty("生成预支付订单的订单id")
    private String prepayid;

    @ApiModelProperty("商家根据财付通文档填写的数据和签名")
    private String packagestr;

    @ApiModelProperty("随机串，防重发 ")
    private String noncestr;

    @ApiModelProperty("时间戳，防重发")
    private String timestamp;

    @ApiModelProperty("商家对数据做的签名")
    private String sign;

    @ApiModelProperty("支付私钥")
    private String privateKey;

    @ApiModelProperty("订单信息")
    private String orderPayInfo;


}
