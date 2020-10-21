/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月18日
 * Description:Notification.java 
 */
package com.ihomefnt.o2o.intf.domain.push.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class NotificationDto {

	private String alert;

	private IOSDto ios;

	private AndroidDto android;
}
