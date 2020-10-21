package com.ihomefnt.o2o.intf.manager.util.common.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class CommonUtil {

	public static String doGet(String urlStr) {
		HttpClient client = new HttpClient();
		String content = null;
		HttpMethod method = null;
		method = new PostMethod(urlStr);
		method.getParams().setContentCharset("utf-8");
		try {
			client.executeMethod(method);
			content = method.getResponseBodyAsString();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return content;
	}
}
