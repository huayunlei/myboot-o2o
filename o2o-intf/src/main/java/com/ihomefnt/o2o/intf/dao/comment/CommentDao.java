package com.ihomefnt.o2o.intf.dao.comment;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.comment.dto.UserCommentDto;


public interface CommentDao {
	
	int addUserComment(Map<String, Object> params);
	
	List<UserCommentDto> queryUserCommentList(Map<String, Object> params);
	
	int queryUserCommentCount(Map<String, Object> params);
}
