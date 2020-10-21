package com.ihomefnt.o2o.service.dao.comment;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.comment.CommentDao;
import com.ihomefnt.o2o.intf.domain.comment.dto.UserCommentDto;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.comment.CommentDao.";

	@Override
	public int addUserComment(Map<String, Object> params) {
		return sqlSessionTemplate.insert(NAME_SPACE + "addUserComment",params);
	}
	
	@Override
	public List<UserCommentDto> queryUserCommentList(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryUserCommentList",params);
	}
	
	@Override
	public int queryUserCommentCount(Map<String, Object> params) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryUserCommentCount",params);
	}

}
