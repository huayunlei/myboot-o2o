package com.ihomefnt.o2o.intf.manager.util.common.http;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 
 * @author Administrator
 * 
 */
public class TrustAnyTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        int a = 1;
        if (a < 0) {
            throw new CertificateException();
        }
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        int a = 1;
        if (a != 1) {
            throw new CertificateException();
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[]{};
    }
}