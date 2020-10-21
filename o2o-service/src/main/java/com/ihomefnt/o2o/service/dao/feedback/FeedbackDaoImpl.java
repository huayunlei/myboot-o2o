package com.ihomefnt.o2o.service.dao.feedback;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.feedback.FeedbackDao;
import com.ihomefnt.o2o.intf.domain.feedback.doo.TFeedbackDto;

/**
 * Created by piweiwen on 15-1-20.
 */
@Repository
public class FeedbackDaoImpl implements FeedbackDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.feedback.FeedbackDao.";

    @Override
    public Long addFeedback(TFeedbackDto feedback) {
        sqlSessionTemplate.insert(NAME_SPACE + "addFeedBack",feedback);
        return feedback.getFeedbackId();
    }
}
