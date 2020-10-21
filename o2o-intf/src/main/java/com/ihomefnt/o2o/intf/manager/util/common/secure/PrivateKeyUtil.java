package com.ihomefnt.o2o.intf.manager.util.common.secure;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by onefish on 2017/8/21 0021.
 * 私钥工具
 */
public class PrivateKeyUtil {
    
    private static final Charset charset = Charset.forName("UTF-8");
    
    private static final int multiplyFactor = 17;
    
    private static final int radix = 33;

    /**
     * privateKey落入db前，经过该方法进行混淆，将混淆后的key落入db
     * @param privateKey
     * @return confusedKey
     */
    public static String confusePrivateKey(String privateKey) {
        BigInteger bigInteger = new BigInteger(1,privateKey.getBytes(charset));
        
        bigInteger = bigInteger.multiply(BigInteger.valueOf(multiplyFactor));
        return bigInteger.toString(radix);
    }


    /**
     * 在使用privateKey前，通过该方法将混淆后的key还原
     * @param confusedKey
     * @return privateKey
     */
    public static String getOriginPrivateKey(String confusedKey) {
        BigInteger bigInteger = new BigInteger(confusedKey, radix);
        bigInteger = bigInteger.divide(BigInteger.valueOf(multiplyFactor));
        return new String(bigInteger.toByteArray(),charset);
    }
    
    
    public static String generateAesKey(String secureRandom) {
        try {
            
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            if (StringUtils.isEmpty(secureRandom)) {
                kg.init(128);
            } else {
                kg.init(128,new SecureRandom(secureRandom.getBytes()));
            }
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            return new String(Base64.encodeBase64(b));
        }
        catch (NoSuchAlgorithmException e) {
            
        }
        return "";
    }

    /**
     * byte数组转化为16进制字符串
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex=Integer.toHexString(bytes[i]);
            if(strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if(strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }

    private static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
        return  temp;
    }
    public static String generateMd5Key()  {
        return  getUUID() ;
    }

//    public static void main(String[] args) {
//        String partnerPriKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDCvAoxVDwt1y7wf0OaWnG8hyP2B81Sqjw0PiqTooy4PWjEwi1breND/J3lJrNiIMU1wsZDLkNhAwT9hRktzbSjl+RUelWs9/rO+dFD34E68W/0j3/fch1gNLDmJHbLTJMTTuP0CUbkStPnBD+HZO+QNu4PycrJNzfH6FS9gpoev6nkW9aaMLJ7gRb0hibnvHb5M3RPXhum1znREChmsX+BD1WDf06crmNbDI+JC1P70ErVDcRmTM5ZjoKGPWxQ4jKqMj3emU+yttSjeu8BPpGN7TKz7+yQ4WruJvKWOHwRPrUwygRNOzzLKDub+GbRuwEsbSVH2cYdAqKW3FPsj7XBAgMBAAECggEBAIIHr8KqnZh6dERwpkJ+HC4oCvIw2YBWXecAj8uPIemwT7H6evEZ1oRCPWiTA/sFKMoyKdMYJDO2IXHrmxmDnxgRx4xFN8pMt7PXCk4JAzDJoNkrrNYWS9k+tdSnCo/LrPWamwoL28N6164NnyIYWwLwOtxeAUOE1IFu+I6mk6TtaiRxzxm0CEOgwgoDrRznhVmL8EU3YsD82sLVjC7NRyjP5+vaU8WQTozz/Hs2KAF3LGLaAsWCUuCAZcau2PFB6p9xSrcOjbhg+k/80FnOoa3s+t/OFibrDNeewYlwMJLZL2DYMYqFoyZMqU777D4emSzKCeUM/GBw7Cw6uC3InDECgYEA4CkgRQHjCxw0T5FCwX1RLSlWvufoeqiOUhQ+5b5gWfC7m8LrI9dgFY+4FHDPc/T/wW7DDRbWlXK0RBp3VLWLOOPv06ZvdU22jFLiqEi21nSGfVXS8aUvWW9bOFy3AexBuutieX3uyudH5HI7Lq+CDQ0CvwmsBpeFjAWUCufyU20CgYEA3mTttV1LReb6VN1NB8ElmKbFNdzJytp5QDDU/JiqZ9ZhQhNCuGYaa4EyOg89IC9xBnu0y6jLw5H30FGG+S44p/LxIPtShfflMvB0tN80Ub57sAWjgAxTUWb0J9QLsynuzUXneoHxjla/SN+o5qq7b2iG8vOOre8Yng04XvbQ4yUCgYA8miHFRktRl6B02nrHwM+PfBudpSju8F75xctzZVK7PiAabkoP+Ixbh+1maVFMsjHq470L7t64rLgbqWQVnLc0/Dq8Z8S+W/I8YbQxducnd31cqTVMazIv5bEtvbMka/EhTb3jxq4mYPzIwDFL39szGA9kAoNljNVoE6IAiNC0yQKBgQCIfkuCt6q+UWAs+9IOQ1Yu1ZROVZ8oOGSYqB/glFZp+qKgqi+V+1yVxKquBbJPca6d+wjgwk2lT45YNeFwSBUPx0SFKJrijF4IoJoQdLmovg8t3pgVu/rbCCQr2bIAmWjuZcwHXX+DC+zE7ji8sebuHqpMrH1SdV+XiaeiaTuVXQKBgDawxKEmmHZocyqMOpgeBWD6cLmB/mdituh3V/eq+iS/cryqNqV7xLh//f/L/CxKkUQM18hmzbgxF/XDC4ySHA6vXSlIcDSk0gyjKr1s2noDoXzIkMYI2vXaYM5KNgRofzaPNxgSHyoVaapZC5RnxqurApq4thw3PxlFQjtTwU4R";
//
//        String confusedKey = confusePrivateKey(partnerPriKey);
//        System.out.println(confusedKey);
//
//        String unconfusedKey = getOriginPrivateKey(confusedKey);
//        System.out.println(unconfusedKey);
//
//        System.out.println(partnerPriKey.equals(unconfusedKey));
//
//        System.out.println(generateAesKey(null));
//    }
    
}

    
