/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年12月20日
 * Description:IPushProxy.java 
 */
package com.ihomefnt.o2o.intf.proxy.push;

import java.util.Map;

import com.ihomefnt.common.api.ResponseVo;

/**
 * 消息推送代理接口
 * 
 * @author zhang
 */
public interface PushProxy {

	/**
	 * push个推
	 * 
	 * @param param
	 * @return
	 */
	ResponseVo<?> sendPushPersonalMessage(Object param);

	/**
	 * push平台推送
	 * 
	 * @param param
	 * @return
	 */
	ResponseVo<?> sendPushPlatformMessage(Object param);

	/**
	 * 发送短信
	 */
	void sendSmsMessageByMobile(Object param);

	/**
	 * 通过ali发送短信
	 * 
	 * @param paramMap
	 * @return
	 */
	boolean sendSmsMessageByAli(Map<String, Object> paramMap);

	/**
	 * 邮件发送
	 */
	void sendMailMessage(Object param);
}
