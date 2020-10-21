/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月18日
 * Description:Extras.java 
 */
package com.ihomefnt.o2o.intf.domain.push.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhang
 */
@Data
public class ExtrasDto {
	
	private String openPage ;
	
	private String msgContent;
	
	private Integer msgType;
	
	private Date createTime;
	
	private Integer saveInMsgCenter;
	
	private String msgImg;
	
	private String msgTitle;
	
	private String msgId;
	
	private Integer unReadCount;
	
	private Integer messageGroupStatus;
	
	private Date sendTime;
}
