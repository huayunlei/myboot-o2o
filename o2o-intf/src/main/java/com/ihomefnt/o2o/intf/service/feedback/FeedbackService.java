package com.ihomefnt.o2o.intf.service.feedback;

import com.ihomefnt.o2o.intf.domain.feedback.doo.TFeedbackDto;


/**
 * Created by piweiwen on 15-1-20.
 */
public interface FeedbackService {
	
	Long addFeedback(TFeedbackDto feedback);
}
