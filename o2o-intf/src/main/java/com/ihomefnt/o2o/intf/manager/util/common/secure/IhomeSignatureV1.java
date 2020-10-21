package com.ihomefnt.o2o.intf.manager.util.common.secure;

import com.ihomefnt.o2o.intf.manager.exception.IhomeSecureException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by onefish on 2017/8/18 0018.
 * 签名类
 * 包含生成签名和验证签名
 */
public class IhomeSignatureV1 {

    /**
     * 生成签名
     * @param content 待签名内容
     * @param charset 编码格式
     * @param privateKey 私钥
     * @return 签名
     * @throws IhomeSecureException
     */
    public static String rsaSign(String content, String charset, String privateKey) throws IhomeSecureException {
        try {
            // 签名数据经过md5编码，避免签名数据过长
            content = MD5Util.MD5Encode(content,charset);
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(priKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException e1) {
            throw new IhomeSecureException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", e1);
        } catch (Exception e2) {
            throw new IhomeSecureException("RSAcontent = " + content + "; charset = " + charset, e2);
        }
    }

    /**
     * 生成签名
     * @param param 待签名内容
     * @param charset 编码格式
     * @param privateKey 私钥
     * @return 签名
     * @throws IhomeSecureException
     */
    public static String rsaSign(Map<String,String> param, String charset, String privateKey) throws IhomeSecureException {
        String content = getSignContent(param);
        return rsaSign(content,charset,privateKey);
    }

    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for(int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            String value = sortedParams.get(key);
            if (StringUtils.areNotEmpty(key, value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                ++index;
            }
        }

        return content.toString();
    }

    public static boolean rsaVerify(Map<String, String> params, String charset, String publicKey) throws IhomeSecureException {
        String sign = params.get("sign");
        String content = getSignCheckContentV2(params);
        // 验证签名和参数是否匹配
        content = MD5Util.MD5Encode(content,charset);
        return rsaCheckContent(content,sign,publicKey,charset);
    }


    public static boolean rsaCheckContent(String content, String sign, String publicKey, String charset) throws IhomeSecureException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(pubKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception var6) {
            throw new IhomeSecureException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, var6);
        }
    }


    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins != null && !StringUtils.isEmpty(algorithm)) {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = StreamUtil.readText(ins).getBytes();
            encodedKey = Base64.decodeBase64(encodedKey);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static String getSignCheckContentV2(Map<String, String> params) {
        if (params == null) {
            return null;
        } else {
            params.remove("sign");
            StringBuffer content = new StringBuffer();
            List<String> keys = new ArrayList(params.keySet());
            Collections.sort(keys);

            for(int i = 0; i < keys.size(); ++i) {
                String key = (String)keys.get(i);
                String value = (String)params.get(key);
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            }

            return content.toString();
        }
    }
    
}
