/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月18日
 * Description:JpushMqComponentConsumer.java 
 */
package com.ihomefnt.o2o.service.service.mq;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.message.annotation.JmsListener;
import com.ihomefnt.message.annotation.RocketListener;
import com.ihomefnt.o2o.intf.domain.push.dto.*;
import com.ihomefnt.o2o.intf.manager.constant.push.PushConstant;
import com.ihomefnt.o2o.intf.service.push.PushService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhang
 */
@Component("jpushMqComponentConsumer")
public class JpushMqComponentConsumer {
	
	@Autowired
	private PushService pushService;
	
	private static final Logger LOG = LoggerFactory.getLogger(JpushMqComponentConsumer.class);

	@JmsListener(destination = "queue.push.origin.jpush")
	@RocketListener(topic = "queue.push.origin.jpush", consumerGroup = "o2o-api")
	public void handleMessage(String message) {
		LOG.info("jpush mq start message = {}", message);
		if(StringUtils.isBlank(message)){
			LOG.info("jpush mq getText is blank");
			return ;
		}
		PushInfoDto info=JsonUtils.json2obj(message, PushInfoDto.class);
		if(info == null){
			LOG.info("jpush  mq getText of bean is blank");
			return ;
		}
		String msgId=info.getSendJPushMessageId();
		if(StringUtils.isBlank(msgId)){
			LOG.info("jpush mq msgId is blank");
			return ;
		}
		String payload =info.getPayload();
		if(StringUtils.isBlank(payload)){
			LOG.info("jpush mq payload is blank");
			return ;
		}
		PayloadDto load =JsonUtils.json2obj(payload, PayloadDto.class);
		if(load ==null){
			LOG.info("jpush mq payload of  bean is blank");
			return ;
		}

		AudienceDto audience =load.getAudience();
		if(audience!=null){
			List<String> alias =audience.getAlias();
			List<String> tags =audience.getTag();
			/**
			 * 第一轮迭代,个推:只用判断别名和tag都不为空
			 */
			if(CollectionUtils.isNotEmpty(alias)&&CollectionUtils.isNotEmpty(tags)){
				List<PushUserCenterInfoDto> list =new ArrayList<PushUserCenterInfoDto>();
				for(String mobile:alias){
					PushUserCenterInfoDto center =new PushUserCenterInfoDto();
					center.setMobile(mobile);
					center.setTags(tags);
					center.setMsgId(msgId);
					center.setCreateTime(new Date());
					center.setSendFlag(PushConstant.SEND_NO);
					list.add(center);
				}
				pushService.insertPushList(list);
			}
			/**
			 * 第二轮迭代,群推:tag不为空,别名为空
			 */
			if(CollectionUtils.isEmpty(alias)&&CollectionUtils.isNotEmpty(tags)){
				PushPlatformCenterInfoDto center =new PushPlatformCenterInfoDto();
				center.setTags(tags);
				center.setMsgId(msgId);
				center.setCreateTime(new Date());
				pushService.insertPushPlatformCenterInfo(center);
			}
		}
		LOG.info("jpush mq end");
	}

}
