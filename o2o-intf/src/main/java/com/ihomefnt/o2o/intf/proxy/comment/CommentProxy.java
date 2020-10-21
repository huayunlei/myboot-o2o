package com.ihomefnt.o2o.intf.proxy.comment;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.comment.dto.CheckCommentDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentLabelsDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsAddDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;

public interface CommentProxy {

	List<CommentLabelsDto> getLabels(Integer type);

	CommentsDto getComment(Integer orderId);

	String addComment(CommentsAddDto params);

	CheckCommentDto checkCommentByOrderId(Integer orderId);

}
