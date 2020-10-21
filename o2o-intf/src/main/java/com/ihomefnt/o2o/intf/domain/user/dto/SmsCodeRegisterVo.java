/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月23日
 * Description:SmsCodeRegisterVo.java 
 */
package com.ihomefnt.o2o.intf.domain.user.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class SmsCodeRegisterVo {

	private String mobile;

	private String smsCode;

	private Integer source;

	private Integer osType;

	private String pValue;

	public String getpValue() {
		return pValue;
	}

	public void setpValue(String pValue) {
		this.pValue = pValue;
	}
}
