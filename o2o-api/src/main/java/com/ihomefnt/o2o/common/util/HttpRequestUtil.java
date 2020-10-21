package com.ihomefnt.o2o.common.util;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpMessage;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JacksonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequestUtil {

	public static HttpBaseResponse httpRequestJson(String url,Object obj){
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		HttpClient httpclient = new DefaultHttpClient();
		HttpParams params = httpclient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 20000);  
        HttpConnectionParams.setSoTimeout(params, 50000);
		HttpPost request = new HttpPost(url);
		try{
			// 先封装一个 JSON 对象
			org.json.JSONObject param = new org.json.JSONObject(obj);
			
			// 绑定到请求 Entry
			StringEntity se = new StringEntity(param.toString(),"UTF-8");
			request.setEntity(se);
			
			// 发送请求
			HttpResponse httpResponse = httpclient.execute(request);
			
	        if(httpResponse.getStatusLine().getStatusCode() == 200){
				// 得到应答的字符串，这也是一个 JSON 格式保存的数据
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
		        baseResponse = (HttpBaseResponse)JacksonUtil.str2Obj(JSONObject.fromObject(retSrc).toString(), HttpBaseResponse.class);
	        } else {
	            baseResponse.setCode(HttpResponseCode.FAILED);
	            baseResponse.setObj(null);
	            HttpMessage message = new HttpMessage();
	            message.setMsg(MessageConstant.FAILED);
	            baseResponse.setExt(message);
	        }
		} catch(Exception e){
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.FAILED);
            baseResponse.setExt(message);
		}finally{
			request.releaseConnection();
			httpclient.getConnectionManager().shutdown();
		}
		return baseResponse;
	}
	
	
	public static String httpRequestJsonStr(String url,Object obj){
		HttpClient httpclient = new DefaultHttpClient();
		HttpParams params = httpclient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 20000);  
        HttpConnectionParams.setSoTimeout(params, 50000);
		HttpPost request = new HttpPost(url);
		String retSrc = "";
		try{
			// 先封装一个 JSON 对象
			org.json.JSONObject param = new org.json.JSONObject(obj);
			
			// 绑定到请求 Entry
			StringEntity se = new StringEntity(param.toString(),"UTF-8");
			request.setEntity(se);
			
			// 发送请求
			HttpResponse httpResponse = httpclient.execute(request);
			
	        if(httpResponse.getStatusLine().getStatusCode() == 200){
				// 得到应答的字符串，这也是一个 JSON 格式保存的数据
				retSrc = EntityUtils.toString(httpResponse.getEntity());
				retSrc = JSONObject.fromObject(retSrc).toString();
	        } 
		} catch(Exception e){

		}finally{
			request.releaseConnection();
			httpclient.getConnectionManager().shutdown();
		}
		return retSrc;
	}
	
	public static HttpBaseResponse httpRequestBean(String url,Object obj){
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		HttpClient httpclient = new DefaultHttpClient();
		HttpParams params = httpclient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 20000);  
        HttpConnectionParams.setSoTimeout(params, 50000);
        HttpPost httpPost = new HttpPost(url);
		try{
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
	        if(null !=obj) {
	        	
		        Map<String, Object> paramMap = JacksonUtil.toMap(obj);
		        if(null != paramMap && paramMap.size() > 0){
		        	for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
		        		if(StringUtils.isNotBlank(entry.getKey())
		        				&& null != entry.getValue() 
		        				&& StringUtils.isNotBlank(entry.getValue().toString())){
		        			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
		        		}
		        	}
		        }
		        
	        }


	        httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));  
	        HttpResponse httpResponse = httpclient.execute(httpPost);
	        if(httpResponse.getStatusLine().getStatusCode() == 200){
				// 得到应答的字符串，这也是一个 JSON 格式保存的数据
				String retSrc = EntityUtils.toString(httpResponse.getEntity());
				JSONObject jsonObj = JSONObject.fromObject(retSrc);
		        baseResponse = (HttpBaseResponse)JacksonUtil.str2Obj(jsonObj.toString(), HttpBaseResponse.class);
	        } else {
	            baseResponse.setCode(HttpResponseCode.FAILED);
	            baseResponse.setObj(null);
	            HttpMessage message = new HttpMessage();
	            message.setMsg(MessageConstant.FAILED);
	            baseResponse.setExt(message);
	        }
	        
		} catch(Exception e){
            baseResponse.setCode(HttpResponseCode.FAILED);
            baseResponse.setObj(null);
            HttpMessage message = new HttpMessage();
            message.setMsg(MessageConstant.FAILED);
            baseResponse.setExt(message);
		}finally{
			httpPost.releaseConnection();
			httpclient.getConnectionManager().shutdown();
		}
		return baseResponse;
	}
	
	
	public static String httpRequestBeanStr(String url,Object obj){
		String retSrc = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpParams params = httpclient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 20000);  
        HttpConnectionParams.setSoTimeout(params, 50000);
        HttpPost httpPost = new HttpPost(url);
		try{
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
	        Map<String, Object> paramMap = JacksonUtil.toMap(obj);
	        if(null != paramMap && paramMap.size() > 0){
	        	for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
	        		if(StringUtils.isNotBlank(entry.getKey())
	        				&& null != entry.getValue() 
	        				&& StringUtils.isNotBlank(entry.getValue().toString())){
	        			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
	        		}
	        	}
	        }

	        httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));  
	        HttpResponse httpResponse = httpclient.execute(httpPost);
	        if(httpResponse.getStatusLine().getStatusCode() == 200){
				// 得到应答的字符串，这也是一个 JSON 格式保存的数据
				retSrc = EntityUtils.toString(httpResponse.getEntity());
				retSrc = JSONObject.fromObject(retSrc).toString();
	        } 
	        
		} catch(Exception e){
			
		}finally{
			httpPost.releaseConnection();
			httpclient.getConnectionManager().shutdown();
		}
		return retSrc;
	}
}
