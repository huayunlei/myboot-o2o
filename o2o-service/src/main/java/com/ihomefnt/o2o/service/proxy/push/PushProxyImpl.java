/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年12月20日
 * Description:PushProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.push;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.proxy.push.PushProxy;
import com.ihomefnt.zeus.finder.ServiceCaller;

/**
 * @author zhang
 */
@Service
public class PushProxyImpl implements PushProxy {

	@Resource
	private ServiceCaller serviceCaller;

	/**
	 * 增加日志:主要为了方便定位
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PushProxyImpl.class);

	@Override
	public ResponseVo<?> sendPushPersonalMessage(Object param) {
		LOG.info("sendPushPersonalMessage start");
		LOG.info("base-api.push.sendPushPersonalMessage params:{}", JsonUtils.obj2json(param));
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("base-api.push.sendPushPersonalMessage", param, ResponseVo.class);
		} catch (Exception e) {
			LOG.error("base-api.push.sendPushPersonalMessage ERROR:{}", e.getMessage());
			return null;
		}
		LOG.info("base-api.push.sendPushPersonalMessage result :{}", JsonUtils.obj2json(responseVo));
		LOG.info("sendPushPersonalMessage end");
		return responseVo;
	}

	@Override
	public ResponseVo<?> sendPushPlatformMessage(Object param) {
		LOG.info("sendPushPlatformMessage start");
		LOG.info("base-api.push.sendPushPlatformMessage params:{}", JsonUtils.obj2json(param));
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("base-api.push.sendPushPlatformMessage", param, ResponseVo.class);
		} catch (Exception e) {
			LOG.error("base-api.push.sendPushPlatformMessage ERROR:{}", e.getMessage());
			return null;
		}
		LOG.info("base-api.push.sendPushPlatformMessage result :{}", JsonUtils.obj2json(responseVo));
		LOG.info("sendPushPlatformMessage end");
		return responseVo;
	}

	@Override
	public void sendSmsMessageByMobile(Object param) {
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("base-api.push.sendSmsMessageByMobile", param, ResponseVo.class);
		} catch (Exception e) {
			LOG.error("base-api.push.sendSmsMessageByMobile ERROR:{}", e.getMessage());
			return;
		}
		LOG.info("base-api.push.sendSmsMessageByMobile result :{}", JsonUtils.obj2json(responseVo));
		LOG.info("sendSmsMessageByMobile end");

	}

	@Override
	public void sendMailMessage(Object param) {

		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("base-api.push.sendMailMessage", param, ResponseVo.class);
		} catch (Exception e) {
			LOG.error("base-api.push.sendMailMessage ERROR:{}", e.getMessage());
			return;
		}
		LOG.info("base-api.push.sendMailMessage result :{}", JsonUtils.obj2json(responseVo));
		LOG.info("sendMailMessage end");

	}

	@Override
	public boolean sendSmsMessageByAli(Map<String, Object> paramMap) {
		LOG.info("serviceCaller post base-api.push.aijia.sms param:{}", paramMap);
		ResponseVo<?> responseVo = null;
		try {
			responseVo = serviceCaller.post("base-api.push.aijia.sms", paramMap, ResponseVo.class);
		} catch (Exception e) {
			LOG.error("base-api.push.aijia.sms ERROR:{}", e.getMessage());
			return false;
		}
		LOG.info("serviceCaller base-api.push.aijia.sms result:{}", JsonUtils.obj2json(responseVo));
		if (responseVo == null) {
			return false;
		}
		if (responseVo.isSuccess()) {
			return true;
		}
		return false;
	}

}
