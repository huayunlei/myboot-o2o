/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年1月17日
 * Description:HttpJpushUpdateRequest.java 
 */
package com.ihomefnt.o2o.intf.domain.push.dto;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * @author zhang
 */
@Data
public class JpushUpdateRequestVo extends HttpBaseRequest {
	
	private List<String> msgIdList;//消息IDs
}
