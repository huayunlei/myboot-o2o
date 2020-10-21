/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月8日
 * Description:AjbService.java 
 */
package com.ihomefnt.o2o.intf.service.ajb;

/**
 * @author zhang
 */
public interface AjbService {

	/**
	 * 新家大晒为精华
	 * 
	 * @param shareOrderId
	 *            新家大晒Id
	 * @param accessToken
	 *            登录标识
	 * @return
	 */
	void addStickyPost(String shareOrderId, String accessToken);

}
