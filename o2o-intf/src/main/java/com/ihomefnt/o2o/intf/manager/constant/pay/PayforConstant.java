/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月14日
 * Description:LianLianConstant.java 
 */
package com.ihomefnt.o2o.intf.manager.constant.pay;

/**
 * @author zhang
 */
public interface PayforConstant {

	String LIAN_LIAN_TAG = "LIAN_LIAN_TAG";

	String OPEN_TAG = "true";

	String ACTUAL_PRODUCT_NO = "109001";// 虚拟商品销售：101001,实物商品销售：109001

	String LIANLIAN_CATEGORY = "4010";
	/**
	 * 1:邮局平邮； 2:普通快递；3:特快专递； 4:物流货运公司； 5:物流配送公司 6:国际快递 7:航运快运 8:海运
	 */
	String DELIVERY_MODE_COMMON = "2";

	/**
	 * 12h: 12 小时内； 24h:24 小时内； 48h:48 小时内； 72h:72 小时内； Other:3 天后
	 */
	String DELIVERY_CYCLE = "72h";

	/**
	 * 用户收货地址取不到,可以去公司的收货地址
	 */
	String PROVINCE_CODE = "320000";// 江苏省

	String CITY_CODE = "320100";// 南京

	String GOODS_COUNT = "1";

	String IDENTIFY_YES = "1";// 是否实名认证:1:是, 0：无认证

	// 实名认证方式:是实名认证时，必填 1：银行卡认证,2：现场认证,3：身份证远程认证,4：其它认证

	String IDENTIFY_CARD_TYPE = "1";
	
	String VERSION_ERROR ="该版本不支持";
	
	/**
     * 微信小程序支付
     */
    Integer TYPE_PAY_WECHAT_APPLET = 5;
    /**
     * 连连的支付枚举值
     */
    Integer TYPE_PAY_LIANLIAN = 3;

	/**
	 * 新连连的支付枚举值
	 */
	Integer TYPE_PAY_NEW_LIANLIAN = 6;
}
