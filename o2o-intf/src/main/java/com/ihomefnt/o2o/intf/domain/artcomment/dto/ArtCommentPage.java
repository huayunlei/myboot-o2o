package com.ihomefnt.o2o.intf.domain.artcomment.dto;

import lombok.Data;

import java.util.List;
@Data
public class ArtCommentPage {

	private List<ArtCommentDto> commentList; // 结果集

	private int totalRecords;// 总记录数

	private int pageSize;// 每页数目

	private int pageNo;// 当前第几页

	private int totalPages;// 总页数

	private int userScore; // 用户打分
}
