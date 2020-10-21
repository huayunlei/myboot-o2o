/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月18日
 * Description:PushUserCenterInfo.java 
 */
package com.ihomefnt.o2o.intf.domain.push.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 消息个推实例
 * 
 * @author zhang
 */
@Data
public class PushUserCenterInfoDto {

	private String msgId; // 消息id

	private String mobile;// 用户手机

	private List<String> tags;// tags

	private Integer sendFlag;// 1送达0未送达

	private Date createTime;// 创建时间
}
