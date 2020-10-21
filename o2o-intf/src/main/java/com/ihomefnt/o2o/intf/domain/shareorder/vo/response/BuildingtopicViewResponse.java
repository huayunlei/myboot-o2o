/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年8月9日
 * Description:BuildingtopicViewResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.shareorder.vo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhang
 */
@Data
public class BuildingtopicViewResponse {

	private String id;// 楼盘专题主键

	private String buildingName;// 楼盘项目

	private String creatorName;// 作者

	private String creatorImg;// 作者头像

	private String headImg;// 专题头图

	private String firstTitle;// 主标题

	private String secondTitle;// 副标题

	private String topicDesc;// 专题描述

	private Date pushTime;// 推送时间

	private String pushTimeDesc;// 推送时间

	private Date statusTime;// 发布时间

	private String statusTimeDesc;// 发布时间

	private List<ContentResponse> contentList;// 添加图片和内容

	private boolean praiseTag;// 是否点赞：是true

	private Integer clickCount; // 点击数量

	private Integer praiseCount; // 点赞数量

	private String content;// 专题正文

	private Integer topicType;// 专题类型

	private String topicName;// 专题类型

	private List<StoryResponse> storyList;// 专题故事列表

	private Integer allowedTag; // 允许投稿:1允许 0不允许

	private String limitTimeDesc;// 投稿截止时间:yyyy-MM-dd HH:mm:ss
	
	private Integer lastAllowed ;//最终是否 允许投稿:1允许 0不允许

	private String author;// 作者

	private String authorImg; // 作者头像
}
