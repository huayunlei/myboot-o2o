package com.ihomefnt.o2o.intf.manager.util.common.wechat;

import com.ihomefnt.common.util.RedisUtil;
import com.ihomefnt.o2o.intf.manager.constant.agent.AgentPraise;
import com.ihomefnt.o2o.intf.manager.constant.weixin.FluxConstant;
import com.ihomefnt.o2o.intf.domain.weixin.vo.request.GetPhoneNumberRequest;
import com.ihomefnt.o2o.intf.manager.util.common.*;
import com.ihomefnt.o2o.intf.manager.util.common.http.CommonUtil;
import com.ihomefnt.o2o.intf.manager.util.common.secure.MySecureProtocolSocketFactory;
import com.ihomefnt.o2o.intf.manager.util.common.secure.ProtocolAlgConstants;
import com.ihomefnt.o2o.intf.manager.util.common.secure.SessionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Weixinutil {
	private static String accessToken;
	private static long createTime = 0;
	private static long expiresTime = 7200;
	public static String jsapiTicket;
	private static long createTime2 = 0;
	private static long expiresTime2 = 7200;
	private static String meetAccessToken;
	public static String meetJsapiTicket;
	private static String wechart_accessToken;
	public static String wechart_jsapiTicket;
	// 调整到1小时50分钟
	public static final long cacheTime = 6600;

	private static final Logger LOG = LoggerFactory.getLogger(Weixinutil.class);
	
	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public static String getAccessToken(String appId, String secret, String weixinTokenFlag) {
		// 不为true 就实时获取
		if (StringUtils.isBlank(weixinTokenFlag) || !weixinTokenFlag.equals("true")) {
			LOG.info("getAccessToken:false");
			getToken(appId, secret);
		} else {
			long offtime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")).getTimeInMillis() - createTime;
			LOG.info("getAccessToken:true");
			// 为true,判断是否超过2个小时
			if (offtime > expiresTime * 1000) {
				LOG.info("getAccessToken:over");
				getToken(appId, secret);
				createTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")).getTimeInMillis();
			}			
		}
		LOG.info("access_token:" + accessToken);
		return accessToken;
	}

	private static void getToken(String appId, String secret) {
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();
		HttpMethod method = null;
		try {
			// 获取access_token的url
			if (StringUtils.isBlank(appId)) {
				appId = Const.APPID;
			}
			if (StringUtils.isBlank(secret)) {
				secret = Const.ACCESS_TOKEN;
			}
			String accessurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
					+ "&secret=" + secret;
			// 使用GET方法
			method = new GetMethod(accessurl);
			method.getParams().setContentCharset("utf-8");
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				String access_token = method.getResponseBodyAsString();
				JSONObject object = new JSONObject(access_token);
				if (null != object.get("access_token")) {
					accessToken = (String) object.get("access_token");
					expiresTime = object.get("expires_in") != null ? object.getLong("expires_in") : 7200;					
				}
			}
			LOG.info("获取access_token=" + accessToken);
		} catch (Exception e) {
			accessToken = null;
			LOG.error("getToken Exception ", e);
		} finally {
			// 释放连接
			if (null != method) {
				// 打印服务器返回的状态
				LOG.info(method.getStatusLine().toString());
				method.releaseConnection();
			}
		}
	}

	public static String getJsapiTicket(String appId, String secret, String weixinTokenFlag) {
		// 不为true 就实时获取
		if (StringUtils.isBlank(weixinTokenFlag) || !weixinTokenFlag.equals("true")) {
			LOG.info("getJsapiTicket:false");
			getTicket(appId, secret, weixinTokenFlag);
		} else {
			long offtime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")).getTimeInMillis() - createTime2;
			LOG.info("getJsapiTicket:true");
			// 为true,判断是否超过2个小时
			if (offtime > expiresTime2 * 1000) {
				LOG.info("getJsapiTicket:over");
				getTicket(appId, secret, weixinTokenFlag);
				createTime2 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")).getTimeInMillis();
			}			
		}
		LOG.info("jsapiTicket:" + jsapiTicket);
		return jsapiTicket;
	}

	private static void getTicket(String appId, String secret, String weixinTokenFlag) {
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();
		HttpMethod method = null;
		try {
			String accessurl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ getAccessToken(appId, secret, weixinTokenFlag) + "&type=jsapi";

			// 使用GET方法
			method = new GetMethod(accessurl);
			method.getParams().setContentCharset("utf-8");
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				String ticket = method.getResponseBodyAsString();
				JSONObject object = new JSONObject(ticket);
				if (null != object && null != object.get("ticket")) {
					jsapiTicket = (String) object.get("ticket");
					expiresTime2 = object.get("expires_in") != null ? object.getLong("expires_in") : 7200;
				}
			}
			LOG.info("获取jsapiTicket=" + jsapiTicket);
		} catch (Exception e) {
			jsapiTicket = null;
			LOG.error("getTicket Exception ", e);
		} finally {
			// 释放连接
			if (null != method) {
				// 打印服务器返回的状态
				LOG.info(method.getStatusLine().toString());
				method.releaseConnection();
			}
		}
	}

	public static String getUserByOpenId(String fromUser) {

		String nickname = "";
		String access_token = Weixinutil.getAccessToken(null, null, "true");

		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&openid=" + fromUser
				+ "&lang=zh_CN";
		String content = CommonUtil.doGet(url);

		int i = 0;
		try {
			JSONObject jsonObj = new JSONObject(content);
			if (null != jsonObj.get("errcode")) {
				Integer errcode = (Integer) jsonObj.get("errcode");
				if (42001 == errcode || 40001 == errcode) {
					i = 1;
				}
			}
		} catch (JSONException e) {

		}
		if (i == 0) {
			try {
				JSONObject jsonObj = new JSONObject(content);

				nickname = jsonObj.getString("nickname");

			} catch (JSONException e) {

			}
		}
		return nickname;
	}

	public static Map<String, String> sign(String url, String appId, String secret, String weixinTokenFlag) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		String ticket = getJsapiTicket(appId, secret, weixinTokenFlag);
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		LOG.info(string1);
		try {
			MessageDigest crypt = MessageDigest.getInstance(ProtocolAlgConstants.SHA_1_ALG);
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			LOG.error("sign NoSuchAlgorithmException ", e);
		} catch (UnsupportedEncodingException e) {
			LOG.error("sign UnsupportedEncodingException ", e);
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		if (StringUtils.isNotBlank(appId)) {
			ret.put("appid", appId);
		} else {
			ret.put("appid", Const.APPID);
		}

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 微信小程序登录获取openid和session_key
	 * 
	 * @param appid
	 * @param appsecret
	 * @param loginCode
	 * @return
	 */
	public static Map<String, Object> oauth2GetOpenid(String appid, String appsecret, String loginCode) {
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();
		HttpMethod method = null;
		Map<String, Object> result = null;
		try {
			// 获取access_token的url
			String accessurl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret="
					+ appsecret + "&js_code=" + loginCode + "&grant_type=authorization_code";
			// 使用GET方法
			method = new GetMethod(accessurl);
			method.getParams().setContentCharset("utf-8");
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				String access_token = method.getResponseBodyAsString();
				JSONObject object = new JSONObject(access_token);
				if (null != object.get("openid")) {
					result = new HashMap<String, Object>();
					result.put("openId", object.get("openid"));
					result.put("sessionKey", object.get("session_key"));
				}
			}
		} catch (Exception e) {
			LOG.error("oauth2GetOpenid Exception ", e);
		} finally {
			// 释放连接
			if (null != method) {
				// 打印服务器返回的状态
				LOG.info(method.getStatusLine().toString());
				method.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * 获取微信公众号AccessToken
	 * 
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static String getWechatAccessToken(String appid, String appsecret) {
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();
		HttpMethod method = null;
		try {
			long offtime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")).getTimeInMillis() - createTime;
			if (offtime > expiresTime * 1000) {
				// 获取access_token的url
				String accessurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ appid + "&secret=" + appsecret;
				// 使用GET方法
				method = new GetMethod(accessurl);
				method.getParams().setContentCharset("utf-8");
				int statusCode = client.executeMethod(method);
				if (statusCode == 200) {
					String access_token = method.getResponseBodyAsString();
					JSONObject object = new JSONObject(access_token);
					if (null != object.get("access_token")) {
						accessToken = (String) object.get("access_token");
						expiresTime = object.get("expires_in") != null ? object.getLong("expires_in") : 7200;
					}
				}
				createTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")).getTimeInMillis();
				LOG.info("获取access_token=" + accessToken);
			}
		} catch (Exception e) {
			LOG.error("getWechatAccessToken Exception ", e);
		} finally {
			// 释放连接
			if (null != method) {
				// 打印服务器返回的状态
				LOG.info(method.getStatusLine().toString());
				method.releaseConnection();
			}
		}
		LOG.info("access_token:" + accessToken);
		return accessToken;
	}

	/**
	 * 跨年小程序
	 * @param url
	 * @param appId
	 * @return
	 */
	public static Map<String, String> meetSign(String url, String appId) {
		//查询redis缓存的数据
		String secret = null;
		if (StringUtils.isBlank(appId)) {
			appId = Const.MEETAPPID;
			secret = Const.MEET_ACCESS_TOKEN;
		}else {
			secret = WxConfigEnum.getEnumByAppId(appId).getSecret();
		}

		String redisKey = "wechat_token_" + appId;
		meetAccessToken = RedisUtil.get(redisKey);
		String redisTicketKey = "wechat_ticket_" + appId;
		meetJsapiTicket = RedisUtil.get(redisTicketKey);
		
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		if(StringUtils.isBlank(meetAccessToken) || StringUtils.isBlank(meetJsapiTicket)){
			getMeetJsapiTicket(appId, secret);
		}
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + meetJsapiTicket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		LOG.info(string1);
		try {
			MessageDigest crypt = MessageDigest.getInstance(ProtocolAlgConstants.SHA_1_ALG);
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			LOG.error("sign NoSuchAlgorithmException ", e);
		} catch (UnsupportedEncodingException e) {
			LOG.error("sign UnsupportedEncodingException ", e);
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", meetJsapiTicket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("accessToken", meetAccessToken);
		if (StringUtils.isNotBlank(appId)) {
			ret.put("appid", appId);
		} else {
			ret.put("appid", Const.MEETAPPID);
		}

		return ret;
	}

	private static void getMeetJsapiTicket(String appId, String secret) {
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();
		HttpMethod method = null;
		try {
			String accessurl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ getMeetAccessToken(appId, secret) + "&type=jsapi";

			// 使用GET方法
			method = new GetMethod(accessurl);
			method.getParams().setContentCharset("utf-8");
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				String ticket = method.getResponseBodyAsString();
				JSONObject object = new JSONObject(ticket);
				if (null != object && null != object.get("ticket")) {
					meetJsapiTicket = (String) object.get("ticket");
					long meetExpiresTime = object.get("expires_in") != null ? object.getLong("expires_in") : 7200;
					
					//保存到redis
					String redisTicketKey = "wechat_ticket_" + appId;
					RedisUtil.set(redisTicketKey, meetJsapiTicket, (int) meetExpiresTime);
				}
			}
			LOG.info("获取meetJsapiTicket=" + meetJsapiTicket);
		} catch (Exception e) {
			meetJsapiTicket = null;
			LOG.error("getMeetJsapiTicket Exception ", e);
		} finally {
			// 释放连接
			if (null != method) {
				// 打印服务器返回的状态
				LOG.info(method.getStatusLine().toString());
				method.releaseConnection();
			}
		}
	}

	private static String getMeetAccessToken(String appId, String secret) {
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();
		HttpMethod method = null;
		try {
			// 获取access_token的url
			if (StringUtils.isBlank(appId)) {
				appId = Const.MEETAPPID;
			}
			if (StringUtils.isBlank(secret)) {
				secret = Const.MEET_ACCESS_TOKEN;
			}
			String accessurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
					+ "&secret=" + secret;
			// 使用GET方法
			method = new GetMethod(accessurl);
			method.getParams().setContentCharset("utf-8");
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				String access_token = method.getResponseBodyAsString();
				JSONObject object = new JSONObject(access_token);
				if (null != object.get("access_token")) {
					meetAccessToken = (String) object.get("access_token");
					long meetExpiresTime = object.get("expires_in") != null ? object.getLong("expires_in") : 7200;	
					
					String redisKey = "wechat_token_" + appId;
					RedisUtil.set(redisKey, meetAccessToken, (int) meetExpiresTime);
				}
			}
			LOG.info("获取meet_access_token=" + meetAccessToken);
		} catch (Exception e) {
			meetAccessToken = null;
			LOG.error("getMeetAccessToken Exception ", e);
		} finally {
			// 释放连接
			if (null != method) {
				// 打印服务器返回的状态
				LOG.info(method.getStatusLine().toString());
				method.releaseConnection();
			}
		}
		
		return meetAccessToken;
	}

	/**
	 * 微信签名
	 * @param url
	 * @param wxType
	 * @return
	 * Author: ZHAO
	 * Date: 2018年6月4日
	 */
	public static Map<String, String> weChartSign(String url, String wxType) {
		String appId = "";
		String secret = "";
		
		//查询redis缓存的数据
		if(wxType.equals("happyhouse")){
			appId = Const.HAPPYHOUSE_APPID;
			secret = Const.HAPPYHOUSE_SECRET;
		}
		if(wxType.equals("henanIhome")){
			appId = Const.HENAN_IHOME_APPID;
			secret = Const.HENAN_IHOME_SECRET;
		}
		if(StringUtils.isBlank(appId) || StringUtils.isBlank(secret)){
			return null;
		}
		
		String redisKey = "wechat_token_" + appId;
		wechart_accessToken = RedisUtil.get(redisKey);
		String redisTicketKey = "wechat_ticket_" + appId;
		wechart_jsapiTicket = RedisUtil.get(redisTicketKey);
		
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		if(StringUtils.isBlank(wechart_accessToken) || StringUtils.isBlank(wechart_jsapiTicket)){
			getWeChartJsapiTicket(appId, secret);
		}
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + wechart_jsapiTicket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		LOG.info(string1);
		try {
			MessageDigest crypt = MessageDigest.getInstance(ProtocolAlgConstants.SHA_1_ALG);
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			LOG.error("weChartSign NoSuchAlgorithmException ", e);
		} catch (UnsupportedEncodingException e) {
			LOG.error("weChartSign UnsupportedEncodingException ", e);
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", wechart_jsapiTicket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("accessToken", wechart_accessToken);
		ret.put("appid", appId);

		return ret;
	}

	private static void getWeChartJsapiTicket(String appId, String secret) {
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();
		HttpMethod method = null;
		try {
			;
			String accessurl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
					+ getWeChartAccessToken(appId, secret) + "&type=jsapi";

			// 使用GET方法
			method = new GetMethod(accessurl);
			method.getParams().setContentCharset("utf-8");
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				String ticket = method.getResponseBodyAsString();
				JSONObject object = new JSONObject(ticket);
				if (null != object && null != object.get("ticket")) {
					wechart_jsapiTicket = (String) object.get("ticket");
					long meetExpiresTime = object.get("expires_in") != null ? object.getLong("expires_in") : 7200;
					
					//保存到redis
					String redisTicketKey = "wechat_ticket_" + appId;
					RedisUtil.set(redisTicketKey, wechart_jsapiTicket, (int) meetExpiresTime);
				}
			}
			LOG.info("获取weChartJsapiTicket=" + wechart_jsapiTicket);
		} catch (Exception e) {
			wechart_jsapiTicket = null;
			LOG.info("获取wechart_jsapiTicket失败", e);
		} finally {
			// 释放连接
			if (null != method) {
				// 打印服务器返回的状态
				LOG.info(method.getStatusLine().toString());
				method.releaseConnection();
			}
		}
	}

	private static String getWeChartAccessToken(String appId, String secret) {
		Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();
		HttpMethod method = null;
		try {
			// 获取access_token的url
			String accessurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
					+ "&secret=" + secret;
			// 使用GET方法
			method = new GetMethod(accessurl);
			method.getParams().setContentCharset("utf-8");
			int statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				String access_token = method.getResponseBodyAsString();
				JSONObject object = new JSONObject(access_token);
				if (null != object.get("access_token")) {
					wechart_accessToken = (String) object.get("access_token");
					long meetExpiresTime = object.get("expires_in") != null ? object.getLong("expires_in") : 7200;	
					
					String redisKey = "wechat_token_" + appId;
					RedisUtil.set(redisKey, wechart_accessToken, (int) meetExpiresTime);
				}
			}
			LOG.info("获取wechart_access_token=" + wechart_accessToken);
		} catch (Exception e) {
			wechart_accessToken = null;
			LOG.info("获取wechart_access_token失败", e);
		} finally {
			// 释放连接
			if (null != method) {
				// 打印服务器返回的状态
				LOG.info(method.getStatusLine().toString());
				method.releaseConnection();
			}
		}
		return wechart_accessToken;
	}

	/**
	 * 获取用户手机号
	 * @param request
	 * @return
	 * Author: ZHAO
	 * Date: 2018年6月21日
	 */
	public static Map<String, Object> getPhoneNumber(GetPhoneNumberRequest request, String wxType) {
		Map<String, Object> result = new HashMap<String, Object>();
		String agentSessionKey = "sessionkey_";
		if(StringUtils.isNotBlank(wxType)){
			agentSessionKey = agentSessionKey + wxType + "_";
		}
		agentSessionKey = agentSessionKey + request.getLoginSessionKey();
		List<String> sessionList = RedisUtil.getList(agentSessionKey);
		if(CollectionUtils.isEmpty(sessionList)){
			return result;
		}
		LOG.info("weixinutil.getPhoneNumber start");
		String json = WXAES.wxDecrypt(request.getEncryptedData(), sessionList.get(0), request.getIv());
		LOG.info("weixinutil.getPhoneNumber end");
		if(StringUtils.isNotBlank(json)){
			JSONObject object;
			try {
				object = new JSONObject(json);
				result.put("phoneNumber", object.get("phoneNumber"));
			} catch (JSONException e) {
				LOG.info("weixinutil.getPhoneNumber error");
			}
		}
		return result;
	}
	
	/**
	 * 获取sessionKey
	 * @return
	 * Author: ZHAO
	 * Date: 2018年6月22日
	 */
	public static Map<String, Object> getSessionKey(GetPhoneNumberRequest request, String wxType){
		//返回结果
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String code = request.getCode();
		String loginSessionKey = "";
		String oldOpenid = "";
		String sessionRedisKey = "sessionkey_";
		if(StringUtils.isNotBlank(wxType)){
			sessionRedisKey = sessionRedisKey + wxType + "_";
		}
		if(StringUtils.isNotBlank(request.getLoginSessionKey())){
			loginSessionKey = request.getLoginSessionKey();//上一次的sessionkey
			//根据sessionkey获取缓存的数据
			String agentSessionKey = sessionRedisKey + loginSessionKey;
			List<String> sessionList = RedisUtil.getList(agentSessionKey);
			if(CollectionUtils.isNotEmpty(sessionList)){
				oldOpenid = sessionList.get(1);
			}
			
			/*Map<String, Object> sessionMap = RedisUtil.getObjectMap(agentSessionKey);
			if(sessionMap != null && sessionMap.get("openId") != null){
				oldOpenid = sessionMap.get("openId").toString();
			}*/
			//删除无用的缓存
			RedisUtil.del(agentSessionKey);
		}
		//根据code获取sessionInfo
		Map<String, Object> keyMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(wxType) && wxType.equals("star")){
			//小星星
			keyMap = oauth2GetOpenid(Const.STAR_APPID, Const.STAR_SECRET, code);
		}else{
			//经纪人
			keyMap = oauth2GetOpenid(AgentPraise.APPLET_ID, AgentPraise.APPLET_SECRET, code);
		}
		if(keyMap != null && StringUtils.isNotBlank(keyMap.get("sessionKey").toString())){
			String newOpenid = keyMap.get("openId").toString();
			if(StringUtils.isNotBlank(oldOpenid) && StringUtils.isNotBlank(newOpenid)){
				//已登陆过
				if(oldOpenid.equals(newOpenid)){
					resultMap.put("loginFlag", 1);//同一账号
				}else{
					resultMap.put("loginFlag", 2);//不同账号
				}
			}else{
				resultMap.put("loginFlag", 0);//首次登录
			}
			//生成loginSessionKey
			loginSessionKey = SessionUtil.generateSessionId();
			List<String> keyList = new ArrayList<String>();
			keyList.add(keyMap.get("sessionKey").toString());
			keyList.add(newOpenid);
			RedisUtil.setList(sessionRedisKey + loginSessionKey, keyList, 0);
		}
		resultMap.put("loginSessionKey", loginSessionKey);
		return resultMap;
	}

	/**
	 * 验证消息真实性
	 *
	 * @param request
	 *            - 微信服务器发送的GET请求，包含signature、timestamp、nonce、echostr 的4个参数
	 * @return true-消息请求来自微信服务器，原样返回echostr参数<br>
	 *         false-消息验证失败
	 */
	public static boolean checkSignature(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		if (signature != null) {
			String[] tmpArr = { FluxConstant.TOKEN, timestamp, nonce };
			Arrays.sort(tmpArr);
			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < tmpArr.length; i++) {
				buf.append(tmpArr[i]);
			}
			if (signature.equals(encrypt(buf.toString()))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对字符串进行sha1加密
	 *
	 * @param strSrc
	 *            - 要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance(ProtocolAlgConstants.SHA_1_ALG);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			LOG.error("encrypt o2o-exception , more info :{}",e.getMessage());
			return null;
		}
		return strDes;
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
}
