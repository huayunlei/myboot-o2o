/**
 * 
 */
package com.ihomefnt.o2o.intf.manager.constant.push;

/**
 * @author zhang
 *
 */
public interface PushConstant {

	//Jpush appKey
	final String appKey = "28c97ed0b241a23f766e26af";//"28c97ed0b241a23f766e26af";
	
	//Jpush masterSecret
	final String masterSecret = "c5667272e06eb5b06c8d8325";//"c5667272e06eb5b06c8d8325";
	
	//通知状态:1生效	0失效
	final Long  NOTICE_STATUS_ENABLE =1L;
	final Long NOTICE_STATUS_DISABLE =0L;
	
	//通知小类：1用户订单配送,2用户订单签收,3用户订单退款,4咨询回复,5现金券到账通知 ,6套装推送,7案例主动通知,8攻略主动通知, 9营销活动<br/>	
	final Long NOTICE_SUBTYPE_DISPACHE=1L;
	final Long NOTICE_SUBTYPE_SIGN=2L;
	final Long NOTICE_SUBTYPE_REFUND=3L;
	final Long NOTICE_SUBTYPE_ANSWER=4L;
	final Long NOTICE_SUBTYPE_COUPON=5L;
	final Long NOTICE_SUBTYPE_SUIT=6L;
	final Long NOTICE_SUBTYPE_CASE=7L;
	final Long NOTICE_SUBTYPE_STRATEGY=8L;
	final Long NOTICE_SUBTYPE_SALE=9L;
	final Long NOTICE_SUBTYPE_ARTICLE=10L; //灵感文章
	
	final Long NOTICE_SUBTYPE_SHAREORDER=18L;//晒家大赛
	
	// 通知or消息:1通知,0消息,2未知
	final Long TYPE_NOTICE=1L;
	final Long TYPE_MESSAGE=0L;
	final Long TYPE_UNKNOWN=2L;
	
	//是否进消息盒子 :1是,0否,2未知
	final Long BOX_STATUS_YES=1L;
	final Long BOX_STATUS_NO=0L;
	final Long BOX_STATUS_UNKNOWN=2L;
	
	//是否是消息组 :1是,0否,2未知
	final Long GROUP_STATUS_YES=1L;
	final Long GROUP_STATUS_NO=0L;
	final Long GROUP_STATUS_UNKNOWN=2L;
	
	//等于符号
	final String EQ_FLAG="\\[eq\\]";
	final String EQ_CHAR="=";
	
	//发送时间类型1：立即，2：定时
	final Long SEND_TIME_NOW =1L;
	final Long SEND_TIME_DELAY=2L;
	
	//消息发送结果状态1成功0失败2未知
	final Long SEND_RESULT_OK=1L;
	final Long SEND_RESULT_FAILED=0L;
	final Long SEND_RESULT_UNKNOWN=2L;
	
	//前台返回消息提示信息
	final String MESSAGE_QUERY_PUSH_DATA_EMPTY="查询消息结果为空";	
	final String MESSAGE_QUERY_CASE_DATA_EMPTY="查询案例或者攻略结果为空";
	final String MESSAGE_QUERY_CONSULT_DATA_EMPTY="查询回复结果为空";
	final String MESSAGE_QUERY_PRODUCT_DATA_EMPTY="查询商品结果为空";
	
	//商品类型1:套装2：空间3：单品
	final Long PRODUCT_TYPE_SUIT=1L;
	final Long PRODUCT_TYPE_ROOM=2L;
	final Long PRODUCT_TYPE_RRODUCT=3L;
	
	//标签类型
	final Long TAG_VERSION=1L;
	final Long TAG_CITY =2L;
	
	//失败描述
	final String ERROR_DESC="Error";
	
	// 1送达0未送达
	final Integer SEND_YES = 1;
	final Integer SEND_NO = 0;
	
	String MONGDB_PUSH_USER ="jpush_user_sendInfo";
	
	String MONGDB_PUSH_PLATFORM ="jpush_platform_sendInfo";
	
	String MONGDB_PUSH_PAYLOAD="jpush_payload";
	
	//1:iPhone客户端，2:Android客户端
	
	Integer IPHONE=1;
	
	Integer ANDRIOD=2;
	
	String SMS_CHANGE_PSD = "o2o_pwd";
}

