package com.ihomefnt.o2o.intf.domain.homecard.dto;

import java.util.List;

/**
 * 用户评论回复数据集合
 * @author ZHAO
 */
public class CommentReplyListResponseVo {
	private List<CommentResponseVo> replyCommentList;//评论回复集合

	public List<CommentResponseVo> getReplyCommentList() {
		return replyCommentList;
	}

	public void setReplyCommentList(List<CommentResponseVo> replyCommentList) {
		this.replyCommentList = replyCommentList;
	}
	
}
