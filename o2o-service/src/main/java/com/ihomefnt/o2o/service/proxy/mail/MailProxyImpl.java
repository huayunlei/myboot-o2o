/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月13日
 * Description:MailProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.mail;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.push.PushProxy;
import com.ihomefnt.o2o.service.manager.config.ApiConfig;
import com.ihomefnt.o2o.intf.proxy.mail.MailProxy;
import com.ihomefnt.o2o.intf.domain.mail.dto.ErrorEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhang
 */
@Service
public class MailProxyImpl implements MailProxy {

	@Autowired
    ApiConfig apiConfig;

	@Autowired
	DicProxy dicProxy;

	@Autowired
	PushProxy pushProxy;

	@Override
	public void sendErrorMail(ErrorEntity info) {
		String wcmEmail = info.getWcmEmail();
		String title = info.getTitle();
		String zeusMethod = info.getZeusMethod();
		Object param = info.getParam();
		ResponseVo<?> responseVo = info.getResponseVo();
		String errorMsg = info.getErrorMsg();
		// 只有满足生产环境才为true
		String openTagShow = apiConfig.getOpenTagShow();
		if (openTagShow.equals("true")
				&& (StringUtils.isNotBlank(errorMsg) || (responseVo != null && !responseVo.isSuccess()))) {
			StringBuilder sb = new StringBuilder();
			sb.append("请求方法: <b>");
			sb.append(zeusMethod);
			sb.append("</b></br>");
			sb.append("请求参数:");
			sb.append(JsonUtils.obj2json(param));
			sb.append("<br/>");
			if (responseVo != null) {
				sb.append("请求结果:");
				sb.append(JsonUtils.obj2json(responseVo));
				sb.append("<br/>");
			}
			if (StringUtils.isNotBlank(errorMsg)) {
				sb.append("请求异常:");
				sb.append(JsonUtils.obj2json(errorMsg));
				sb.append("<br/>");
			}
			// 创建邮件
			JSONObject mail = new JSONObject();
			DicDto dic = dicProxy.queryDicByKey(wcmEmail);
			if (dic != null && StringUtils.isNotBlank(dic.getValueDesc())) {
				String valueDesc = dic.getValueDesc();
				String[] receiverAddress = valueDesc.split(",");
				mail.put("receiverAddress", receiverAddress);
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				mail.put("subject", sdf.format(new Date()) + title);
				mail.put("mailContent", sb.toString());
				// 发送邮件
				pushProxy.sendMailMessage(mail);
			}
		}
	}

}
