package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by piweiwen on 15-1-20.
 */
@Data
public class FeedbackRequestVo extends HttpBaseRequest{
	private String content;
    private String phoneNumber;
    /** 0-意见反馈 1-咨询问题 */
    private Long type;
}
