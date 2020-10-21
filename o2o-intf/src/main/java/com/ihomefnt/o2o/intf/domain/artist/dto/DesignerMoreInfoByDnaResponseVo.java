/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年3月6日
 * Description:DesignerMoreInfoByDnaResponseVo.java 
 */
package com.ihomefnt.o2o.intf.domain.artist.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class DesignerMoreInfoByDnaResponseVo {

	/**
	 * 用户id（对应用户中心主键）
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
	private String designerBackImage;

	/**
	 * 设计师手机号
	 */
	private String designerMobile;

	/**
	 * 设计师总的佣金（目前用不上）
	 */
	private BigDecimal totalCommission;

	/**
	 * 设计师标签
	 */
	private String designerTag;

	/**
	 * 设计师个人描述
	 */
	private String designerProfile;
	
	private Integer totalSize;

	/**
	 * 当前页设计师dna信息
	 */
	private List<DnaInfoResponse> dnaInfos;
}
