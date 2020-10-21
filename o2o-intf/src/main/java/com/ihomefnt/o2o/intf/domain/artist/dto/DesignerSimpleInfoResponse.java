/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年3月6日
 * Description:DesignerSimpleInfoResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class DesignerSimpleInfoResponse {

	/**
	 * DNA ID
	 */
	private Integer dnaId;

	/**
	 * 设计师用户id（对应用户中心主键）
	 */
	private Integer userId;

	/**
	 * 设计师姓名
	 */
	private String designerName;

	/**
	 * 设计师头像
	 */
	private String designerImage;

	/**
	 * 设计师背图
	 */
	private String desingerBackImage;

	/**
	 * 设计师手机号
	 */
	private String designerMobile;

	/**
	 * 设计师标签
	 */
	private String designerTag;

	/**
	 * 设计师个人描述
	 */
	private String designerProfile;

	/**
	 * DNA 音频
	 */
	private String dnaAudio;

	/**
	 * DNA 视频
	 */
	private String dnaVideo;
}
