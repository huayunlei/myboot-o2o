/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月18日
 * Description:Options.java 
 */
package com.ihomefnt.o2o.intf.domain.push.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class OptionsDto {
	
	private long sendno;
	
	private long time_to_live;
	
	private boolean apns_production;
}
