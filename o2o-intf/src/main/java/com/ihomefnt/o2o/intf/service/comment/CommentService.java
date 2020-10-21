package com.ihomefnt.o2o.intf.service.comment;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.comment.dto.CommentLabelsDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.domain.comment.vo.request.CommentsAddRequestVo;
import com.ihomefnt.o2o.intf.domain.comment.vo.request.UserCommentRequestVo;
import com.ihomefnt.o2o.intf.domain.comment.vo.response.UserCommendResponseVo;

public interface CommentService {

	List<CommentLabelsDto> getLabels(Integer type);

	CommentsDto getComment(Integer orderId);

	String addComment(CommentsAddRequestVo comments);

	boolean isCanComment(Integer orderId);

	boolean isAlreadyComment(Integer orderId);
	
    UserCommendResponseVo queryUserCommentList(UserCommentRequestVo request);
	
	
}
