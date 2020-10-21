/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年10月23日
 * Description:RegisterRequestVo.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class RegisterRequestVo {

	private String name; // 设计师名称

	private String mobile;// 设计师手机号码

	private String openId;// 小程序微信openId

	private String image;// 设计师头像

	private String smsCode;// 短信验证码

	private Integer source;// 来源

	private Integer osType;// 操作系统类型
}
