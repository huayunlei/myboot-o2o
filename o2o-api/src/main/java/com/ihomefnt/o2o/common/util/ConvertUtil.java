package com.ihomefnt.o2o.common.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

/**
 * 类型转换
 * 
 * @ClassName: (ConvertUtil.java)
 * 
 * @Description:
 * 
 * @Date: 2011-8-24 上午02:52:04
 * @Author chymilk
 * @Version 1.0
 */
public final class ConvertUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ConvertUtil.class);

	private final static String DEAFULT_ENCODE = "UTF-8";
	static {
		final String patterns[] = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
				"yyyy-MM-dd HH:mm", "yyyyMMdd", "yyyyMMddmm", "yyyyMMddHHmmss" };
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(patterns);
		ConvertUtils.register(dc, Date.class);
	}

	private ConvertUtil() {
	}

	public static int getInteger(Object obj) {
		return (Integer) ConvertUtils.convert(obj, Integer.class);
	}

	public static String getString(Object obj) {
		return ConvertUtils.convert(obj);
	}

	public static boolean getBoolean(Object obj) {
		return (Boolean) ConvertUtils.convert(obj, Boolean.class);
	}

	public static Date getDate(Object obj) {
		return (Date) ConvertUtils.convert(obj, Date.class);
	}
	
	public static Long getLong(Object obj) {
		return (Long) ConvertUtils.convert(obj, Long.class);
	}

	public static String toLowerCase(String str) {
		return str.trim().toLowerCase();
	}

	public static String toUpperCase(String str) {
		return str.trim().toUpperCase();
	}


	public static String blob2String(Object obj) {
		return blob2String(obj, DEAFULT_ENCODE);
	}
	
	public static String blob2String(Object obj, String encode) {
		Blob blob = (Blob) obj;
		if (blob == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		try (InputStream inputStream = blob.getBinaryStream();
			 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
					 inputStream, encode));) {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (SQLException e) {
			LOG.error("SQLException ", e);
		} catch (IOException e) {
			LOG.error("IOException ", e);
		}
		return sb.toString();
	}

	public static byte[] blob2Img(Object obj){
		Blob blob = (Blob) obj;
		byte[] data = null;
		try (InputStream inStream = blob.getBinaryStream();) {
			long nLen = blob.length();
			int nSize =(int)nLen;
			data = new byte[nSize];
			if (inStream.read(data) > 0) {
				return data;
			}
		}catch (IOException e){
			LOG.error("IOException ", e);
		} catch (SQLException e) {
			LOG.error("SQLException ", e);
		}
		return data;
	}
	
}
