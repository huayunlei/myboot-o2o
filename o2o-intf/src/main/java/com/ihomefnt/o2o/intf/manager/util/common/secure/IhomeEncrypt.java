package com.ihomefnt.o2o.intf.manager.util.common.secure;

import com.ihomefnt.o2o.intf.manager.exception.IhomeSecureException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Created by onefish on 2017/8/18 0018.
 */
public class IhomeEncrypt {
    private static final String AES_ALG = "AES";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";

    public IhomeEncrypt() {
    }

    static SecureRandom random = new SecureRandom();

    public static String encryptContent(String content, String encryptType, String encryptKey, String charset) throws IhomeSecureException {
        if ("AES".equals(encryptType)) {
            return aesEncrypt(content, encryptKey, charset);
        } else {
            throw new IhomeSecureException("当前不支持该算法类型：encrypeType=" + encryptType);
        }
    }

    public static String decryptContent(String content, String encryptType, String encryptKey, String charset) throws IhomeSecureException {
        if ("AES".equals(encryptType)) {
            return aesDecrypt(content, encryptKey, charset);
        } else {
            throw new IhomeSecureException("当前不支持该算法类型：encrypeType=" + encryptType);
        }
    }

    private static String aesEncrypt(String content, String aesKey, String charset) throws IhomeSecureException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            byte[] bytesIV = initIv("AES/CBC/PKCS5Padding");
            random.nextBytes(bytesIV);
            IvParameterSpec iv = new IvParameterSpec(bytesIV);
            cipher.init(1, new SecretKeySpec(Base64.decodeBase64(aesKey.getBytes()), "AES"), iv);
            byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));
            return new String(Base64.encodeBase64(encryptBytes));
        } catch (Exception e) {
            throw new IhomeSecureException("AES加密失败：Aescontent = " + content + "; charset = " + charset, e);
        }
    }

    private static String aesDecrypt(String content, String key, String charset) throws IhomeSecureException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            byte[] bytesIV = initIv("AES/CBC/PKCS5Padding");
            random.nextBytes(bytesIV);
            IvParameterSpec iv = new IvParameterSpec(bytesIV);
            cipher.init(2, new SecretKeySpec(Base64.decodeBase64(key.getBytes()), "AES"), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.decodeBase64(content.getBytes()));
            return new String(cleanBytes, charset);
        } catch (Exception e) {
            throw new IhomeSecureException("AES解密失败：Aescontent = " + content + "; charset = " + charset, e);
        }
    }

    private static byte[] initIv(String fullAlg) {
        byte[] iv;
        int i;
        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            iv = new byte[blockSize];

            for(i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        } catch (Exception e) {
            int blockSize = 16;
            iv = new byte[blockSize];

            for(i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        }
    }
    
}
