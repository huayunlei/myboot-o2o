package com.ihomefnt.o2o.intf.manager.util.common.secure;

import com.ihomefnt.o2o.intf.manager.exception.BusinessException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class SessionUtil {
	private static final int SESSION_ID_BYTES = 16;

	public static synchronized String generateSessionId() {
        Random random = new SecureRandom();  // 取随机数发生器, 默认是SecureRandom
        byte bytes[] = new byte[SESSION_ID_BYTES];
        random.nextBytes(bytes); //产生16字节的byte
        MessageDigest messageDigest = getDigest();
        if (null == messageDigest) {
            throw new BusinessException("getDigest is null ");
        }
        bytes = messageDigest.digest(bytes); // 取摘要,默认是"MD5"算法
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {     //转化为16进制字符串
            byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
            byte b2 = (byte) (bytes[i] & 0x0f);
            if (b1 < 10)
                result.append((char) ('0' + b1));
            else
                result.append((char) ('A' + (b1 - 10)));

            if (b2 < 10)
                result.append((char) ('0' + b2));
            else
                result.append((char) ('A' + (b2 - 10)));
        }
        return (result.toString());
    }

	private static MessageDigest getDigest() {
		try {
			MessageDigest md = MessageDigest.getInstance(ProtocolAlgConstants.MD5_ALG);
			return md;
	
		} catch (NoSuchAlgorithmException e) {
			//e.printStackTrace();
			return null;
		}
	}
}
