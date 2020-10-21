package com.ihomefnt.o2o.intf.manager.constant.proxy;

/** 
* @ClassName: AccountWebServiceNameConstants 
* @Description: account-web服务名称常量池 
* @author huayunlei
* @date Feb 21, 2019 2:31:52 PM 
*  
*/
public interface AccountWebServiceNameConstants {

	/**
	 * 艾积分账户充值
	 */
	String ACCOUNT_RECHARGE = "account-web.account.api.recharge";
	
	/**
	 * 查询用户艾积分信息
	 */
	String QUERY_AJB_DETAIL_INFO_BY_USER_ID = "account-web.account.newAjb.queryAjbDetailInfoByUserId";
	
	/**
	 * 查询账本记录
	 */
	String QUERY_BOOK_RECORDS = "account-web.account.newAjb.queryBookRecords";
	
	/**
	 * 订单确认支付（线上）
	 */
	String ACCOUNT_CONFIRM_PAY = "account-web.account.api.confirmPay";
	
	/**
	 * 订单支付冻结（线上）
	 */
	String ACCOUNT_FREEZE_PAY = "account-web.account.api.freezePay";
	
	String QUERY_AJB_ACCOUNT_BY_USER_ID_FOR_APP = "account-web.account.newAjb.queryAjbAccountByUserIdForApp";

	String QUERY_PAY_FINISHED_RECORD_LIST = "account-web.account.api.queryPayFinishedRecordList";
}
