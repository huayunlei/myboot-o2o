package com.ihomefnt.o2o.intf.domain.collage.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jerfan cang
 * @date 2018/10/17 18:52
 */
@Data
@ApiModel("WechatPayResponseVo")
public class WechatPayResponseVo {

         /*"timeStamp": "1539850030",
        "package": "prepay_id=wx18160710429917fc8f262fd82897762046",
        "paySign": "72E0049C326ECAA71EE97FFBB8FBA418D6F60CBB0602E98F6A4398A1C61E12BB",
        "appId": "wx4b23054d5f5e9005",
        "signType": "HMAC-SHA256",
        "nonceStr": "Nvy79w7qxx7aZ1w82d0n1Pb3UitvPKKo"*/

    @ApiModelProperty("商家根据财付通文档填写的数据和签名 fgw给的是 package")
    private String packageStr;

    @ApiModelProperty("时间戳，防重发")
    private  String timeStamp;

    @ApiModelProperty("签名 sign")
    private String paySign;

    @ApiModelProperty("公众号id")
    private String appId;

    @ApiModelProperty("签名方式")
    private String signType;

    @ApiModelProperty("随机串，防重发")
    private  String nonceStr;
}
