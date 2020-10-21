/**
 * 
 */
package com.ihomefnt.o2o.intf.manager.constant.weixin;

/**
 * @author zhang
 *
 */
public interface FluxConstant {

	Long CODE_FLUX_ACTIVITY = 15L;// 微信领取流量活动编码

	/**
	 * 1领取成功 2传入参数为空 3活动未开始 4活动已结束 5用户未关注 6用户已领取 7领取异常 8未知异常
	 */
	String MESSAGE_SUCCESS = "成功";
	String MESSAGE_DATA_EMPTY = "传入参数为空";
	String MESSAGE_NOT_START = "活动未开始";
	String MESSAGE_FINISHED = "活动已结束";
	String MESSAGE_NOT_FOCUS = "用户未关注";
	String MESSAGE_ACCPETED = "用户已领取";
	String MESSAGE_ACCPETED_EXCEPTION = "领取异常";
	String MESSAGE_UNKNOWN_EXCEPTION = "未知异常";
	
	Long CODE_SUCCESS = 1L;
	Long CODE_DATA_EMPTY = 2L;
	Long CODE_NOT_START = 3L;
	Long CODE_FINISHED = 4L;
	Long CODE_NOT_FOCUS = 5L;
	Long CODE_ACCPETED = 6L;
	Long CODE_ACCPETED_EXCEPTION = 7L;
	Long CODE_UNKNOWN_EXCEPTION = 8L;
	
	String APP_ID="wx8f0d4e48400d32d8"; //第三方用户唯一凭证 
	String APP_SECRET="9c10f1cf7c4e98bd49415bbe11a3278f";//第三方用户唯一凭证密钥，即appsecret 
	String GRANT_TYPE="client_credential";//获取access_token填写client_credential 
	//获取access_token_url 每次获取，都会导致上次access_token失效;每次access_token有效期为两个小时
	String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 获取关注用户openId列表 
	String GET_USER_OPENID_LIST_FIRST_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
	String GET_USER_OPENID_LIST_NEXT_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	//获取用户UnionID
	String GET_USER_UNIONID_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	int STATUS_FOCUS=1 ;//关注类型：1 关注 0 非关注
	int STATU_FOCUS_NOT=0;
	
	String CALLBACK_URL="http://api.ihomefnt.com/flux/callback";
	String TOKEN="wx8f0d4e48400d32d8";
	String EncodingAESKey="uezyK4iFZbPco5rltgk8Lkmw4oyGZGiSjpmJ4ZOFmGw";
}
