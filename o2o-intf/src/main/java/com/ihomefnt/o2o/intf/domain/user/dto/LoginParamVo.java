/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月22日
 * Description:LoginParamVo.java 
 */
package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class LoginParamVo {

	private String account;// 用户手机,不能为空

	private String password;// 密码,不能为空

	private String loginIp;// 用户ip地址

	private String deviceId;// 设备id

	private Integer osType;// 设备类型
	
	private int source;// iPhone:2 ,Android:3,H5:4,PC:5,默认:0
}
