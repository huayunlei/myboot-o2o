package com.ihomefnt.o2o.intf.manager.util.common.secure;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;


/*
 '微信支付服务器签名支付请求请求类
 '============================================================================
 'api说明：
 'init(app_id, app_secret, partner_key, app_key);
 '初始化函数，默认给一些参数赋值，如cmdno,date等。
 'setKey(key_)'设置商户密钥
 'getLasterrCode(),获取最后错误号
 'GetToken();获取Token
 'getTokenReal();Token过期后实时获取Token
 'createMd5Sign(signParams);生成Md5签名
 'genPackage(packageParams);获取package包
 'createSHA1Sign(signParams);创建签名SHA1
 'sendPrepay(packageParams);提交预支付
 'getDebugInfo(),获取debug信息
 '============================================================================
 '*/
public class RequestHandler {
	/** Token获取网关地址地址 */
	private String tokenUrl;
	/** 预支付网关url地址 */
	private String gateUrl;
	/** 查询支付通知网关URL */
	private String notifyUrl;
	/** 商户参数 */
	private String appid;
	private String appkey;
	private String partnerkey;
	private String appsecret;
	private String key;
	/** 请求的参数 */
	private SortedMap parameters;
	/** Token */
	private String Token;
	private String charset;
	/** debug信息 */
	private String debugInfo;
	private String last_errcode;

	private HttpServletRequest request;

	private HttpServletResponse response;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(RequestHandler.class);

	public RequestHandler() {
		this.charset = CHAR_SET;
	}


	private final static String CHAR_SET = "UTF-8";
	/**
	 * 初始构造函数。
	 * 
	 * @return
	 */
	public RequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		this.last_errcode = "0";
		this.request = request;
		this.response = response;
		// this.charset = "GBK";
		this.charset = CHAR_SET;
		this.parameters = new TreeMap();
		// 验证notify支付订单网关
		notifyUrl = "https://gw.tenpay.com/gateway/simpleverifynotifyid.xml";

	}

	/**
	 * 初始化函数。
	 */
	public void init(String app_id, String app_secret, String partner_key) {
		this.last_errcode = "0";
		this.Token = "token_";
		this.debugInfo = "";
		this.appid = app_id;
		this.partnerkey = partner_key;
		this.appsecret = app_secret;
		this.key = partner_key;
	}

	/**
	 * 获取最后错误号
	 */
	public String getLasterrCode() {
		return last_errcode;
	}

	/**
	 * 获取入口地址,不包含参数值
	 */
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	 * 获取参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @return String
	 */
	public String getParameter(String parameter) {
		String s = (String) this.parameters.get(parameter);
		return (null == s) ? "" : s;
	}

	// 设置微信密钥
	public void setAppKey(String key) {
		this.appkey = key;
	}

	// 特殊字符处理
	public String UrlEncode(String src) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, this.charset).replace("+", "%20");
	}

	// 获取package的签名包
	public String genPackage(SortedMap<String, String> packageParams)
			throws UnsupportedEncodingException {
		String sign = createSign(packageParams);

		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + UrlEncode(v) + "&");
		}

		// 去掉最后一个&
		String packageValue = sb.append("sign=" + sign).toString();
		// System.out.println("UrlEncode后 packageValue=" + packageValue);
		return packageValue;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		System.out.println("md5 sb:" + sb);
		String sign = MD5Util.MD5Encode(sb.toString(), this.charset)
				.toUpperCase();
		System.out.println("packge签名:" + sign);
		return sign;

	}

	/**
	 * 创建package签名
	 */
	public boolean createMd5Sign(String signParams) {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		// 算出摘要
		String enc = TenpayUtil.getCharacterEncoding(this.request,
				this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();

		String tenpaySign = this.getParameter("sign").toLowerCase();

		// debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign + " tenpaySign:"
				+ tenpaySign);

		return tenpaySign.equals(sign);
	}

	// 输出XML
	public String parseXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"appkey".equals(k)) {

				sb.append("<" + k + ">" + getParameter(k) + "</" + k + ">\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 设置debug信息
	 */
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}

	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}

	public String getDebugInfo() {
		return debugInfo;
	}

	public String getKey() {
		return key;
	}
	
	
	/**
	 * 获取微信静默授权
	* @Title: getWxAutoUnionID 
	* @param @return
	* @return String 
	* @date 2016年8月8日上午9:21:55
	* @author Charl
	 * @throws UnsupportedEncodingException 
	 */
	public  String getWxAutoUnionID(String redirectUrl) throws UnsupportedEncodingException {
		   LOG.info("RequestHandler======getWxAutoUnionID start");
	       String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
	       		+ "wx6e6da3d54cbf104e"
	       		+ "&redirect_uri="+UrlEncode(redirectUrl).toString()
	       		+ "&response_type=code"
	       		+ "&scope=snsapi_base"
	       		+ "&state=234#wechat_redirect";
	       LOG.info("RequestHandler======getWxAutoUnionID:url="+url);
	       String result = getUrl(url);
	       LOG.info("RequestHandler======getWxAutoUnionID:result="+result);
	       JSONObject jsonObject;
	       jsonObject = JSONObject.fromObject(result);
	       String code = jsonObject.getString("code");
	       LOG.info("RequestHandler======getWxAutoUnionID:code="+code);
	       return code;
	   }
	
   /**
    * 获取用户openId
   * @Title: getOpenID 
   * @param @param accessToken
   * @param @return
   * @return String 
   * @date 2016年8月8日下午2:03:21
   * @author Charl
    */
   public  String getOpenID(String code) {

       String url = "https://api.weixin.qq.com/sns/oauth2/access_token"
       		+ "?appid=wx6e6da3d54cbf104e"
       		+ "&secret=88def669f09b71cf89bee5b7cbfb9992"
       		+ "&code="+code
       		+ "&grant_type=authorization_code";

       String openId = null;

       try {

           URL urlGet = new URL(url);
           LOG.info("RequestHandler======getOpenID,url="+url);
           HttpURLConnection http = (HttpURLConnection) urlGet

                   .openConnection();

           http.setRequestMethod("GET"); // 必须是get方式请求

           http.setRequestProperty("Content-Type",

                   "application/x-www-form-urlencoded");

           http.setDoOutput(true);

           http.setDoInput(true);

           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒

           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

           http.connect();

           InputStream is = http.getInputStream();

           int size = is.available();

           byte[] jsonBytes = new byte[size];

           if (is.read(jsonBytes) > 0) {
			   String message = new String(jsonBytes, CHAR_SET);
			   JSONObject demoJson = JSONObject.fromObject(message);
			   openId = demoJson.getString("openid");
		   }
           LOG.info("RequestHandler======getOpenID,openId="+openId);

           is.close();

       } catch (Exception e) {
		   LOG.error("getOpenID Exception ", e);
       }

       return openId;

   }
   
   /**
    * 获取url
   * @Title: getUrl 
   * @param @param url
   * @param @return
   * @return String 
   * @date 2016年8月8日上午9:56:53
   * @author Charl
    */
   public String getUrl(String url){
       String result = null;
       try (CloseableHttpClient httpClient = new DefaultHttpClient();) {
           // 根据地址获取请求
           HttpGet request = new HttpGet(url);
           // 通过请求对象获取响应对象
           HttpResponse response = httpClient.execute(request);
           
           // 判断网络连接状态码是否正常(0--200都数正常)
           if (response.getStatusLine().getStatusCode() == 200) {
		       LOG.info("RequestHandler======getUrl,getEntity="+response.getEntity());
               result= EntityUtils.toString(response.getEntity());
           } 
       } catch (Exception e) {
		   LOG.error("getUrl Exception ", e);
       }
       return result;
   }

}
