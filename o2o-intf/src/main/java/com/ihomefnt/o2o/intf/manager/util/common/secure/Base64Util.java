package com.ihomefnt.o2o.intf.manager.util.common.secure;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;



public class Base64Util {
    
    public static String encode(byte[] binaryData) throws UnsupportedEncodingException {
		if (binaryData == null) {
			return null;
		}
        return new String(Base64.encodeBase64(binaryData), "UTF-8");
    }
    
    public static byte[] decode(String base64String) throws UnsupportedEncodingException {
    	if(StringUtils.isBlank(base64String)){
    	   return null;
    	}
        return Base64.decodeBase64(base64String.getBytes("UTF-8"));
    }
    

}
