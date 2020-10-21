package com.ihomefnt.o2o.intf.manager.util.common.http;

import com.ihomefnt.o2o.intf.manager.util.common.secure.ProtocolAlgConstants;
import org.apache.http.conn.ssl.SSLSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class MySSLSocketFactory extends SSLSocketFactory {

	static {
		mySSLSocketFactory = new MySSLSocketFactory(createSContext());
	}

	private static MySSLSocketFactory mySSLSocketFactory = null;

	private static SSLContext createSContext() {
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance(ProtocolAlgConstants.SSL_ALG);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			if (sslcontext != null) {
				sslcontext.init(null,
						new TrustManager[] { new TrustAnyTrustManager() }, null);
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
			return null;
		}
		return sslcontext;
	}

	private MySSLSocketFactory(SSLContext sslContext) {
		super(sslContext);
		this.setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	public static MySSLSocketFactory getInstance() {
		if (mySSLSocketFactory == null) {
			mySSLSocketFactory = new MySSLSocketFactory(createSContext());
		}
		return mySSLSocketFactory;
	}

}