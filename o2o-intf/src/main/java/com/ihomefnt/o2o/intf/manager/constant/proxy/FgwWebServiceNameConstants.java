package com.ihomefnt.o2o.intf.manager.constant.proxy;

/** 
* @ClassName: FgwWebServiceNameConstants 
* @Description: fgw-web服务名称常量池 
* @author huayunlei
* @date Feb 14, 2019 1:48:38 PM 
*  
*/
public interface FgwWebServiceNameConstants {

	String BANK_INFO_CHECK_AND_RECORD = "fgw-web.common.api.bankInfoCheckAndRecord";
	
	String LIANLIAN_APP_CARDBIN = "fgw-web.lianlian.app.cardbin";
	
	String QUERY_ALL_SUPPORT_PAY_BANK_INFO = "fgw-web.lianlian.app.queryAllSupportPayBankInfo";
	
	String QUERY_EACH_CARD_INFO = "fgw-web.lianlian.app.query.eachCardInfo";
	
	String SIGN_AND_RECORD = " fgw-web.lianlian.app.signAndRecord";
	
	String QUERY_ACTIVATION_BANK_INFO_BY_USER_ID = "fgw-web.lianlian.app.queryActivationBankInfoByUserId";
	
	String UN_BIND_CARD = "fgw-web.lianlian.app.unbindCard";
	
	/**
     * 统一支付 WeChat Alipay lianlian
     */
	String PULL_ONLINE_PAY = "fgw-web.pay.pullOnlinePay";

	String QUERY_ORDER_PAID_INFO = "fgw-web.pay.queryOrderPaidInfo";
}
