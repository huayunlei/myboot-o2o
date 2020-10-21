/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月13日
 * Description:MailProxy.java
 */
package com.ihomefnt.o2o.intf.proxy.mail;

import com.ihomefnt.o2o.intf.domain.mail.dto.ErrorEntity;

/**
 * @author zhang
 */
public interface MailProxy {
	/**
	 * 1.满足生产环境<br/>
	 * 2.errorMsg不为空;或者responseVo不为空,但是responseVo.isSuccess为false<br/>
	 * 同时满足1.2才会发送邮件<br/>
	 * 
	 * @param info
	 */
	void sendErrorMail(ErrorEntity info);

}
