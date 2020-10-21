package com.ihomefnt.o2o.intf.manager.util.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexUtil {

	/**
	 * 手机号码正则
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){
		return isRegexValue("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$",mobiles);
	}
	
	/**
	 * 手机号码正则
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNew(String mobiles){
		return isRegexValue("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$",mobiles);
	}
	
	
	/**
	 * 抛出异常则正则有问题
	 * @param regex
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("finally")
	public static boolean  isRegexValue(String regex,String msg){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(msg);
		return m.matches();
	}
	/**
	 * 数字字符串自动截取
	 * @param saleOff
	 * @return
	 */
	public static String regExSaleOff(String saleOff){
		if(saleOff!=null){
			Float saleLong = Float.parseFloat(saleOff);
			DecimalFormat df = new DecimalFormat("#,##0.0"); 
			saleOff=df.format(saleLong);
			saleLong = Float.parseFloat(saleOff);
			if(0<saleLong&&saleLong<10){
				if(saleOff.indexOf(".0")!=-1){
					saleOff=saleOff.substring(0,1);
				}
			}else{
				saleOff="-1";
			}
			
		}else{
			saleOff="-1";
		}
		return saleOff;
	}

	/**
	 * 将数字转为千位分割格式
	 * @param price
	 * @return
	 */
	public static String parseNumber(BigDecimal price){
		DecimalFormat df = new DecimalFormat(",###,###");
		return df.format(price);
	}
	
	
	/** 
	* @Title: regexNickName 
	* @Description: 要求昵称为英文或汉字、数字、“_”、"-"组成，限4~16个字符（一般一个汉字为2个字符）
	* @param @param str
	* @param @return  参数说明 
	* @return boolean    返回类型 
	* @throws 
	*/
	public static boolean regexNickName(String str) {   
    	if(str.length()<2||str.length()>16){
    		return false;
    	}
        return isRegexValue("^[\u4e00-\u9fa5_a-zA-Z0-9\\-]+$", str);
     }
	
}
