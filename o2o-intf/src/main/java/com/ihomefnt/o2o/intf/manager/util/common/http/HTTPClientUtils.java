package com.ihomefnt.o2o.intf.manager.util.common.http;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ihomefnt.o2o.intf.domain.emchat.dto.Constants;
import com.ihomefnt.o2o.intf.manager.constant.emchat.Credential;
import com.ihomefnt.o2o.intf.manager.constant.emchat.HTTPMethod;
import com.ihomefnt.o2o.intf.manager.constant.emchat.Token;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTTPClient 工具类
 * 
 * @author Lynch 2014-09-15
 *
 */
public class HTTPClientUtils {

	private static final JsonNodeFactory factory = new JsonNodeFactory(false);

	/**
	 * Send SSL Request
	 * 
	 * @return
	 */
	public static ObjectNode sendHTTPRequest(URL url, Credential credential, Object dataBody, String method) {


		ObjectNode resObjectNode = factory.objectNode();

		try (CloseableHttpClient httpClient = getClient(true);){
			HttpResponse response = null;

			if (method.equals(HTTPMethod.METHOD_POST)) {
				HttpPost httpPost = new HttpPost(url.toURI());

				if (credential != null) {
					Token.applyAuthentication(httpPost, credential);
				}
				httpPost.setEntity(new StringEntity(dataBody.toString(), "UTF-8"));

				response = httpClient.execute(httpPost);
			} else if (method.equals(HTTPMethod.METHOD_PUT)) {
				HttpPut httpPut = new HttpPut(url.toURI());
				if (credential != null) {
					Token.applyAuthentication(httpPut, credential);
				}
				httpPut.setEntity(new StringEntity(dataBody.toString(), "UTF-8"));

				response = httpClient.execute(httpPut);
			} else if (method.equals(HTTPMethod.METHOD_GET)) {

				HttpGet httpGet = new HttpGet(url.toURI());
				if (credential != null) {
					Token.applyAuthentication(httpGet, credential);
				}

				response = httpClient.execute(httpGet);

			} else if (method.equals(HTTPMethod.METHOD_DELETE)) {
				HttpDelete httpDelete = new HttpDelete(url.toURI());

				if (credential != null) {
					Token.applyAuthentication(httpDelete, credential);
				}

				response = httpClient.execute(httpDelete);
			}

			if (null != response) {
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					String responseContent = EntityUtils.toString(entity, "UTF-8");
					EntityUtils.consume(entity);

					ObjectMapper mapper = new ObjectMapper();
					JsonFactory factory = mapper.getJsonFactory();
					JsonParser jp = factory.createJsonParser(responseContent);

					resObjectNode = mapper.readTree(jp);
					resObjectNode.put("statusCode", response.getStatusLine().getStatusCode());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return resObjectNode;
	}

	/**
	 * DownLoadFile whit Jersey
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static File downLoadFile(URL url, Credential credential, List<NameValuePair> headers, File localPath) {


		try (CloseableHttpClient httpClient = getClient(true);
			 FileOutputStream fos = new FileOutputStream(localPath);) {
			HttpGet httpGet = new HttpGet(url.toURI());

			if (credential != null) {
				Token.applyAuthentication(httpGet, credential);
			}
			for (NameValuePair header : headers) {
				httpGet.addHeader(header.getName(), header.getValue());
			}

            HttpResponse response = httpClient.execute(httpGet);

			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();

			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = in.read(buffer)) != -1) {
				fos.write(buffer, 0, len1);
			}

		} catch (Exception e) {
			throw new RuntimeException();
		}

		return localPath;
	}

	/**
	 * UploadFile whit Jersey
	 * 
	 * @return
	 */
	public static ObjectNode uploadFile(URL url, File file, Credential credential, List<NameValuePair> headers)
			throws RuntimeException {
		ObjectNode resObjectNode = factory.objectNode();

		try (CloseableHttpClient httpClient = getClient(true);) {
			HttpPost httpPost = new HttpPost(url.toURI());

			if (credential != null) {
				Token.applyAuthentication(httpPost, credential);
			}
			for (NameValuePair header : headers) {
				httpPost.addHeader(header.getName(), header.getValue());
			}

			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file, MediaType.APPLICATION_OCTET_STREAM);
			mpEntity.addPart("file", cbFile);
			httpPost.setEntity(mpEntity);

            HttpResponse response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			if (null != entity) {
				String responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);

				ObjectMapper mapper = new ObjectMapper();
				JsonFactory factory = mapper.getJsonFactory();
				JsonParser jp = factory.createJsonParser(responseContent);

				resObjectNode = mapper.readTree(jp);
				resObjectNode.put("statusCode", response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}

		return resObjectNode;
	}

	/**
	 * Create a CloseableHttpClient instance
	 * 
	 * @param isSSL
	 * @return CloseableHttpClient instance
	 */
	public static CloseableHttpClient getClient(boolean isSSL) throws NoSuchAlgorithmException, KeyManagementException {
		CloseableHttpClient httpClient = new DefaultHttpClient();
		if (isSSL) {
			TrustManager xtm = new TrustAnyTrustManager();

			SSLContext ctx = SSLContext.getInstance("TLS");

			ctx.init(null, new TrustManager[] { xtm }, null);

			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
		}

		return httpClient;
	}

	public static URL getURL(String path) {
		URL url = null;

		try {
			url = new URL(Constants.API_HTTP_SCHEMA, Constants.API_SERVER_HOST, "/" + path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return url;
	}

	/**
	 * Check illegal String
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		return matcher.lookingAt();
	}
}
