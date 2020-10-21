package com.ihomefnt.o2o.intf.manager.util.common.wechat;

import com.ihomefnt.o2o.intf.manager.util.common.http.HttpClientConnectionManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GetWxOrderno {
	public static DefaultHttpClient httpclient;

	private static final String CHARSET = "UTF-8";

	static {
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager
				.getSSLInstance(httpclient);
	}

	public static String getPayNo(String url, String xmlParam) {
		String prepay_id = "";
		try (CloseableHttpClient client = new DefaultHttpClient();){
			client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
					true);
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
			httpost.setEntity(new StringEntity(xmlParam, CHARSET));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), CHARSET);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			System.out.println(jsonStr);

			if (jsonStr.indexOf("FAIL") != -1) {
				return prepay_id;
			}
			Map map = doXMLParse(jsonStr);
			if (null != map && null != map.get("prepay_id")) {
				prepay_id = (String) map.get("prepay_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prepay_id;
	}
	
	/**
	 * 取消订单反馈结果
	 */
	public static String closePayNo(String url, String xmlParam) {
		String jsonStr ="";
		try (CloseableHttpClient client = new DefaultHttpClient();){
			client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
					true);
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
			httpost.setEntity(new StringEntity(xmlParam, CHARSET));
			HttpResponse response = httpclient.execute(httpost);
			jsonStr = EntityUtils.toString(response.getEntity(), CHARSET);
		} catch (Exception e) {		
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 */
	public static Map doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

}