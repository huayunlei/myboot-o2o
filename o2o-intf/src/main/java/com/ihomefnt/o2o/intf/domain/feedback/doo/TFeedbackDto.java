package com.ihomefnt.o2o.intf.domain.feedback.doo;

import lombok.Data;

import java.sql.Timestamp;


/**
 * Created by piweiwen on 15-1-20.
 */
@Data
public class TFeedbackDto {

	/** 主鍵 */
	private Long feedbackId;
	private String content;
	private String phoneNumber;
	private Long userId;
	/** 0-意见反馈 1-咨询问题 */
	private Long type;
	private Timestamp feedbackTime;


	public Timestamp getFeedbackTime() {
		return (null == feedbackTime) ? null : ((Timestamp) feedbackTime
				.clone());
	}

	public void setFeedbackTime(Timestamp feedbackTime) {
		if (null == feedbackTime) {
			this.feedbackTime = null;
		} else {
			this.feedbackTime = (Timestamp) feedbackTime.clone();
		}
	}
}
