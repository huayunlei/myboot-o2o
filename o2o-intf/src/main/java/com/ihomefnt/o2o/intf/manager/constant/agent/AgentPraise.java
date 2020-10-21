package com.ihomefnt.o2o.intf.manager.constant.agent;

/**
 * 经纪人
 * @author ZHAO
 */
public interface AgentPraise {
	//微信小程序
	String APPLET_ID = "wx15b827492e6edfd5"; //第三方用户唯一凭证 
	
	String APPLET_SECRET = "50c0bea83956bf83585eb5054b15f960";//第三方用户唯一凭证密钥，即appsecret 
	
	//微信公众号
	String WECHAT_ID = "wx6e6da3d54cbf104e"; //第三方用户唯一凭证 
	
	String WECHAT_SECRET = "54banPgni1WmcfppMxo0e6kAZVFReJ6D";//第三方用户唯一凭证密钥，即appsecret  
		
	//经纪人状态: 0审核中，1正式合作，2已驳回，3禁用中
	Integer AGENT_STATUS_INAUDIT = 0;//审核中
	
	Integer AGENT_STATUS_FORMAL = 1;//正式合作
	
	Integer AGENT_STATUS_REJECT = 2;//已驳回
	
	Integer AGENT_STATUS_DISABLE = 3;//禁用中
	
	//佣金状态列表，为空则返回所有状态。状态：1待付款，2付款中，3已付款，4已驳回
	Integer COMMISSION_STATUS_WAITPAY = 1;//待付款
	
	Integer COMMISSION_STATUS_ONTPAY = 2;//付款中
	
	Integer COMMISSION_STATUS_PAID = 3;//已付款、付款成功
	
	Integer COMMISSION_STATUS_REJECT = 4;//已驳回、付款失败
	
	String COMMISSION_STATUS_WAITPAY_DESC = "待付款";//待付款
	
	String COMMISSION_STATUS_ONTPAY_DESC = "付款中";//付款中
	
	String COMMISSION_STATUS_PAID_DESC = "已付款";//已付款
	
	String COMMISSION_STATUS_REJECT_DESC = "已驳回";//已驳回
	
	String AGENT_RULE = "AGENT_RULE";//经纪人规则
	
	String AGENT_ROLE = "13";//经纪人角色 
	
	//交易类型
	Integer TRANSACTION_TYPE_RECEIVE = 1;//收款
	
	Integer TRANSACTION_TYPE_REFUND = 2;//退款
	
	//订单阶段（退款）
	Integer REFUND_STATUS_HANDSEL = 31;//定金
	
	Integer REFUND_STATUS_SIGN = 32;//签约
	
	String PAYMENT_TYPE_PURPOSE_DESC = "诚意金";

	String PAYMENT_TYPE_HANDSEL_DESC = "定金";

	String PAYMENT_TYPE_SIGN_DESC = "合同额";

	String PAYMENT_TYPE_CANCEL_DESC = "退款";
	
	//是否是当前经纪人标志
	Integer SELF_FLAG_TRUE = 1;//是
	
	Integer SELF_FLAG_FALSE = 0;//否

	String REGISTRATION_SERVICE_AGREEMENT = "REGISTRATION_SERVICE_AGREEMENT";//经纪人注册服务协议

	String ART_STAR_DEFAULT_NICKNAME = "小星星";//默认昵称
}
