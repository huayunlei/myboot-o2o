/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年3月2日
 * Description:DesignerResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.homecard.vo.response;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class DesignerResponse {

	/**
	 * 设计师id
	 */
	private Integer designerId;

	/**
	 * 设计师头像
	 */
	private String headImgUrl;

	/**
	 * 设计师背图
	 */
	private String desingerBackImage;

	/**
	 * 设计师名称
	 */
	private String designerName;

	/**
	 * 设计师标签
	 */
	private String designerLables;

	/**
	 * 视频链接
	 */
	private String videoUrl;

	/**
	 * 音频链接
	 */
	private String audioUrl;

}
