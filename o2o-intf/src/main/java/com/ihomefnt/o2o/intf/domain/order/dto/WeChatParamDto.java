package com.ihomefnt.o2o.intf.domain.order.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeChatParamDto implements Serializable{

	private static final long serialVersionUID = 7969467311642800893L;
	/** 由用户微信号和AppID组成的唯一标识，发送请求时第三方程序必须填写，用于校验微信用户是否换号登录*/
	private String appid;
	/** 商家向财付通申请的商家id */
	private String partnerid;
	/** 预支付订单 */
	private String prepayid;
	/** 商家根据财付通文档填写的数据和签名 */
	private String packagestr;
	/** 随机串，防重发 */
	private String noncestr;
	/** 时间戳，防重发 */
	private String timestamp;
	/** 商家根据微信开放平台文档对数据做的签名 */
	private String sign;
	private String singType;
}
