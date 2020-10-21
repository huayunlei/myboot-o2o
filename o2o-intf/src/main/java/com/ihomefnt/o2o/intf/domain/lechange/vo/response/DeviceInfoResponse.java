/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年6月29日
 * Description:DeviceInfoResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.lechange.vo.response;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class DeviceInfoResponse {

	private String deviceId;

	private String name;

	private Integer companyId;

	private String companyName;

	private Integer deviceStatus;
}
