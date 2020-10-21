package com.ihomefnt.o2o.intf.domain.emchat.dto;

import com.ihomefnt.o2o.intf.manager.util.common.http.HTTPClientUtils;

import java.net.URL;

/**
 * Created by wangxiao on 2015-11-30.
 */
public interface EndPoints {

	static final URL TOKEN_APP_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/token");

	static final URL USERS_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users");

	static final URL MESSAGES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/messages");

	static final URL CHATGROUPS_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups");
	
	static final URL CHATROOMS_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatrooms");

	static final URL CHATFILES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatfiles");

}
