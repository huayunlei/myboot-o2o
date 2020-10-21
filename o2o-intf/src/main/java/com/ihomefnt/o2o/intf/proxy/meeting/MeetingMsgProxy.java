package com.ihomefnt.o2o.intf.proxy.meeting;

import java.util.Map;

import com.ihomefnt.o2o.intf.domain.meeting.dto.AddMessageDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.QueryMessageListDto;

public interface MeetingMsgProxy {

	/**
	 * 发布留言
	 * 
	 * @param params
	 * @return
	 */
	AddMessageDto publishMsg(Map<String, Object> params);

	/**
	 * 获取留言
	 * 
	 * @param params
	 * @return
	 */
	QueryMessageListDto queryMsg(Map<String, Object> params);
}
