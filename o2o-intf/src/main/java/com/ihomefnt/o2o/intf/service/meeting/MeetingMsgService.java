package com.ihomefnt.o2o.intf.service.meeting;

import com.ihomefnt.o2o.intf.domain.meeting.vo.request.AddMessageRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.request.QueryMessageRequest;
import com.ihomefnt.o2o.intf.domain.meeting.vo.response.AddMessageResponse;
import com.ihomefnt.oms.trade.util.PageModel;

/**
 * 家庭成员管理
 * 
 * @author czx
 */
public interface MeetingMsgService {

	/**
	 * 发布留言
	 * 
	 * @param request
	 * @return
	 */
	AddMessageResponse publishMsg(AddMessageRequest request);

	/**
	 * 获取留言
	 * 
	 * @param request
	 * @return
	 */
	PageModel queryMsg(QueryMessageRequest request);

}
