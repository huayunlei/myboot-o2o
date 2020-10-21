package com.ihomefnt.o2o.intf.domain.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author liguolin
 * 2018-8-1 09:47:01
 */
@Data
@ApiModel("拉起线上支付请求参数信息")
public class OnlinePayParamsDto implements Serializable {

    private static final long serialVersionUID = 7969467311642800893L;

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

    /**
     * 渠道来源WX("WX", 1), ALIPAY("ALIPAY", 2), LIANLIAN("LIANLIAN", 3)
     */
    @ApiModelProperty("渠道来源")
    private int channelSource;
}
