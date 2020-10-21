/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月23日
 * Description:SmsLoginUserParamVo.java 
 */
package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class SmsLoginUserParamVo {

	private String mobile; // 手机号码

	private String smsCode;// 短信编码

	private String loginIp;// 登录ip地址

	private String deviceId;// 设备id

	private Integer osType;// 设备类型

	private int source;// iPhone:2 ,Android:3,H5:4,PC:5,默认:0
}
