/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月9日
 * Description:ArtCommentPageListResponse.java 
 */
package com.ihomefnt.o2o.intf.domain.artcomment.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author zhang
 */
@Data
public class ArtCommentPageListResponse {

	/**
	 * 用户评价
	 */
	private List<ArtCommentResponse> commentList;

	private int totalRecords;// 总记录数

	private int pageSize;// 每页数目

	private int pageNo;// 当前第几页

	private int totalPages;// 总页数

	private int userScore; // 用户打分
}
