package com.ihomefnt.o2o.intf.domain.emchat.dto;

/**
 * Created by wangxiao on 2015-11-30.
 */
public interface Constants {

	// API_HTTP_SCHEMA
	public static String API_HTTP_SCHEMA = "https";
	// API_SERVER_HOST
	public static String API_SERVER_HOST = PropertiesUtils.getProperties().getProperty("API_SERVER_HOST");
	// APPKEY
	public static String APPKEY = PropertiesUtils.getProperties().getProperty("APPKEY");
	// APP_CLIENT_ID
	public static String APP_CLIENT_ID = PropertiesUtils.getProperties().getProperty("APP_CLIENT_ID");
	// APP_CLIENT_SECRET
	public static String APP_CLIENT_SECRET = PropertiesUtils.getProperties().getProperty("APP_CLIENT_SECRET");
	// DEFAULT_PSD
	public static String DEFAULT_PSD = "123456";
	// FILE_PATH
	public static String FILE_PATH = PropertiesUtils.getProperties().getProperty("FILE_PATH");
	// STR_DEFAULT_KEY
	public static String STR_DEFAULT_KEY = PropertiesUtils.getProperties().getProperty("STR_DEFAULT_KEY");
}
