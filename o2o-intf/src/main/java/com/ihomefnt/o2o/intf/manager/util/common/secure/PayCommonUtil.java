package com.ihomefnt.o2o.intf.manager.util.common.secure;

public class PayCommonUtil {

	public static String setXML(String return_code, String return_msg) {

        return "<xml><return_code><![CDATA[" + return_code

                + "]]></return_code><return_msg><![CDATA[" + return_msg

                + "]]></return_msg></xml>";
	}
}
