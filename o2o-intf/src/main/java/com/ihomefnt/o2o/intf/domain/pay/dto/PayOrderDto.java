/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月14日
 * Description:PayOrder.java 
 */
package com.ihomefnt.o2o.intf.domain.pay.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class PayOrderDto {

	public static final String SIGN_TYPE_RSA = "RSA";
	public static final String SIGN_TYPE_MD5 = "MD5";

	private String oid_partner; // 商户编号是商户在连连钱包支付平台上开设的商户号码，为18位数字，如：201304121000001004
	private String notify_url; // 连连钱包支付平台在用户支付成功后通知商户服务端的地址
	private String busi_partner; // 虚拟商品销售：101001,实物商品销售：109001
	private String no_order; // 商户系统唯一订单号
	private String dt_order; // 格式：YYYYMMDDH24MISS 14位数字，精确到秒
	private String no_goods;

	private String name_goods; // 商品名称
	private String money_order; // 该笔订单的资金总额，单位为RMB-元。大于0的数字，精确到小数点后两位。如：49.65
	private String sign_type; // 参与签名
	private String info_order; // 订单描述
	private String bank_code; // 银行编号
	private String force_bank; // 是否强制使用该银行的银行卡标志(0为不强制，1为强制)
	private String pay_type; // 支付方式(2:快捷支付,D 认证支付)
	private String valid_order; // 订单有效期
	private String risk_item; // 风控字段
	private String sign;
	private String token;
	private String gatewayUrl;

	private String repayment_no;// 分期计划编号
	private String repayment_plan;// 分期计划
	private String sms_param;// 短信参数

	// 以下字段不参与签名
	private String id_type;
	private String id_no;
	private String acct_name;
	private String no_agree; // 银行卡协议号 支付成功后返回的，如果是卡前置，需要传入。
	private String card_no; // 银行卡号 卡前置，卡首次支付的时候传入，卡历次支付传入对应协议号就可以
	private String flag_modify; // 修改前置姓名、身份证号的标识 1为不可修改 0为可修改
	private String user_id; // 用户id
	private String shareing_data; // 分账信息
	private String payload;// 加密区域
	private Integer platform;// 平台来源标示
	private String product_type;// 用于区别同pay_product下不同产品, 比如赋值"cheyifu"即为车易付
}
