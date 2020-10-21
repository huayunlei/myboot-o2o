package com.ihomefnt.o2o.service.proxy.meeting;

import com.ihomefnt.o2o.intf.domain.meeting.dto.AddMessageDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.QueryMessageListDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.meeting.MeetingMsgProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 家庭成员管理
 * 
 * @author czx
 */
@Service
public class MeetingMsgProxyImpl implements MeetingMsgProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public AddMessageDto publishMsg(Map<String, Object> params) {
		HttpBaseResponse<AddMessageDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_MSG_PUBLISH_MSG, params,
					new TypeReference<HttpBaseResponse<AddMessageDto>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		return responseVo.getObj();
	}

	@Override
	public QueryMessageListDto queryMsg(Map<String, Object> params) {
		HttpBaseResponse<QueryMessageListDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_MSG_QUERY_MSG, params,
					new TypeReference<HttpBaseResponse<QueryMessageListDto>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		return responseVo.getObj();
	}

}
