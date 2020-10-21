/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月9日
 * Description:ArtCommentResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.artcomment.vo.response;

import com.ihomefnt.o2o.intf.domain.homecard.dto.CommentReply;
import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class ArtCommentResponse {

	/**
	 * 评价主键
	 */
	private String commentId;

	/**
	 * 发表内容
	 */
	private String userComment;

	/**
	 * 用户打星
	 */
	private Integer userStar;

	/**
	 * 发表图片
	 */
	private List<ArtCommentImage> images;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户头像
	 */
	private String nickImg;

	/**
	 * 创建时间
	 */
	private String createTimeStr;

	private List<CommentReply> commentReplyList;//评论回复集合

}