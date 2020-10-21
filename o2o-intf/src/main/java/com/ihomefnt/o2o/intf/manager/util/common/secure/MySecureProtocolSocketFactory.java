package com.ihomefnt.o2o.intf.manager.util.common.secure;

import com.ihomefnt.o2o.intf.manager.util.common.http.TrustAnyTrustManager;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 自动让httpclient接收ssl所有的证书
 * @author chengang
 * @CreateDate 2013年5月16日9:56:18
 *
 */
public class MySecureProtocolSocketFactory implements ProtocolSocketFactory {

    private SSLContext sslcontext = null;

    private SSLContext createSSLContext() {
        SSLContext sslcontext2=null;
        try {
            sslcontext2 = SSLContext.getInstance(ProtocolAlgConstants.SSL_ALG);
            sslcontext2.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslcontext2;
    }
   
    private SSLContext getSSLContext() {
        if (this.sslcontext == null) {
            this.sslcontext = createSSLContext();
        }
        return this.sslcontext;
    }
   
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        SSLContext sslContext = getSSLContext();
        if (sslContext == null) {
            return null;
        }
        return sslContext.getSocketFactory().createSocket(socket,host,port,autoClose);
    }

    public Socket createSocket(String host, int port) throws IOException,UnknownHostException {
        SSLContext sslContext = getSSLContext();
        if (sslContext == null) {
            return null;
        }
        return sslContext.getSocketFactory().createSocket(host,port);
    }
   
   
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException, UnknownHostException {
        SSLContext sslContext = getSSLContext();
        if (sslContext == null) {
            return null;
        }
        return sslContext.getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    public Socket createSocket(String host, int port, InetAddress localAddress,int localPort, HttpConnectionParams params) throws IOException,UnknownHostException, ConnectTimeoutException {
        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        SSLContext sslContext = getSSLContext();
        if (sslContext == null) {
            return null;
        }
        SocketFactory socketfactory = sslContext.getSocketFactory();
        if (timeout == 0) {
            return socketfactory.createSocket(host, port, localAddress, localPort);
        } else {
            return ControllerThreadSocketFactory.createSocket(
                    this, host, port, localAddress, localPort, timeout);
        }
    }
   
}