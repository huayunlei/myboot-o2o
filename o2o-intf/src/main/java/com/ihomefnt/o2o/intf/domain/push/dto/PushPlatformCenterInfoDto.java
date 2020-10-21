/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月21日
 * Description:PushPlatformCenterInfo.java 
 */
package com.ihomefnt.o2o.intf.domain.push.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 消息群推实例
 * 
 * @author zhang
 */
@Data
public class PushPlatformCenterInfoDto {

	private String msgId; // 消息id

	private List<String> tags;// tags

	private Date createTime;// 创建时间
}
