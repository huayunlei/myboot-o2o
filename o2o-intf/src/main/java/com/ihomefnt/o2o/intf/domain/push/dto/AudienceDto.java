/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月18日
 * Description:Audience.java 
 */
package com.ihomefnt.o2o.intf.domain.push.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class AudienceDto {
	
	private List<String> tag;
	
	private List<String> alias;
}
