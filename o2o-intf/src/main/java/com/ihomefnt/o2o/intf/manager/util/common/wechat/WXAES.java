package com.ihomefnt.o2o.intf.manager.util.common.wechat;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

public class WXAES {
	// 算法名
    public static final String KEY_NAME = "AES";
    // 加解密算法/模式/填充方式
    // ECB模式只用密钥即可对数据进行加密解密，CBC模式需要添加一个iv
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    private static Key key;  
    private static Cipher cipher;

    /**
     * 微信 数据解密<br/>
     * 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充<br/>
     * 对称解密的目标密文:encrypted=Base64_Decode(encryptData)<br/>
     * 对称解密秘钥:key = Base64_Decode(session_key),aeskey是16字节<br/>
     * 对称解密算法初始向量:iv = Base64_Decode(iv),同样是16字节<br/>
     *
     * @param encrypted 目标密文
     * @param session_key 会话ID
     * @param iv 加密算法的初始向量
     */
    public static String wxDecrypt(String encrypted, String session_key, String iv) {
        String json = null;
        byte[] encrypted64 = Base64.decodeBase64(encrypted);
        byte[] key64 = Base64.decodeBase64(session_key);
        byte[] iv64 = Base64.decodeBase64(iv);
        try {
            init(key64);
            json = new String(decrypt(encrypted64, key64, generateIV(iv64)),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 初始化密钥
     */
    public static void init(byte[] keyBytes) throws Exception {
    	// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要  
        int base = 16;  
        if (keyBytes.length % base != 0) {  
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);  
            byte[] temp = new byte[groups * base];  
            Arrays.fill(temp, (byte) 0);  
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);  
            keyBytes = temp;  
        }  
        // 初始化  
        Security.addProvider(new BouncyCastleProvider());  
        // 转化成JAVA的密钥格式  
        key = new SecretKeySpec(keyBytes, KEY_NAME);  
        try {  
            // 初始化cipher  
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
        } catch (NoSuchAlgorithmException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        KeyGenerator.getInstance(KEY_NAME).init(128);
    }

    static SecureRandom random = new SecureRandom();

    /**
     * 生成iv
     */
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        // iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
        // Arrays.fill(iv, (byte) 0x00);
        AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_NAME);
        random.nextBytes(iv);
        params.init(new IvParameterSpec(iv));
        return params;
    }

    /**
     * 生成解密
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv)
            throws Exception {
        // 设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedData);
    }
}
