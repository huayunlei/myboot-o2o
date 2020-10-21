/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年8月9日
 * Description:ContentResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.shareorder.vo.response;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class ContentResponse {

	private Integer type;// 1:图片,2:内容

	private String content;// 图片或内容

}
