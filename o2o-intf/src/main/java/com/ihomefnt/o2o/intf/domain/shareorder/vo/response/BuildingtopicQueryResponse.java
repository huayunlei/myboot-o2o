/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年8月11日
 * Description:BuildingtopicQueryResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.shareorder.vo.response;

import com.ihomefnt.o2o.intf.domain.program.dto.ImageEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author zhang
 */
@Data
public class BuildingtopicQueryResponse {

	private String id;// 楼盘专题主键

	private String buildingName;// 楼盘项目

	private String creatorName;// 创建人

	private String creatorImg;// 创建人头像

	private String headImg;// 专题头图

	private ImageEntity headImgData;//

	private String firstTitle;// 主标题

	private String secondTitle;// 副标题

	private String topicDesc;// 专题描述

	private Date pushTime;// 发布时间

	private String pushTimeDesc;// 发布时间
	
	private Date statusTime;// 发布时间

	private String statusTimeDesc;// 发布时间

	private boolean praiseTag;// 是否点赞：是true
	
	private Integer clickCount; // 点击数量

	private Integer praiseCount;// 点赞数量

	private String openPage;// 跳转链接

	private String author; // 作者

	private String authorImg;// 作者头像

	private Integer topicType;// 专题类型
	
	private String topicName;//专题名称
}
