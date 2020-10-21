package com.ihomefnt.o2o.intf.dao.feedback;

import com.ihomefnt.o2o.intf.domain.feedback.doo.TFeedbackDto;


/**
 * Created by piweiwen on 15-1-20.
 */
public interface FeedbackDao {
	Long addFeedback(TFeedbackDto feedback);
}
