package com.ihomefnt.o2o.service.proxy.meeting;

import com.ihomefnt.o2o.intf.domain.meeting.dto.AddMemberDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.UpdateMemberDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.meeting.MeetingFamilyProxy;
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
public class MeetingFamilyProxyImpl implements MeetingFamilyProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public AddMemberDto saveMember(Map<String, Object> params) {
		HttpBaseResponse<AddMemberDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_FAMILY_SAVE_MEMBER, params,
					new TypeReference<HttpBaseResponse<AddMemberDto>>() {
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
	public UpdateMemberDto updateMember(Map<String, Object> params) {
		HttpBaseResponse<UpdateMemberDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_FAMILY_UPDATE_MEMBER, params,
					new TypeReference<HttpBaseResponse<UpdateMemberDto>>() {
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
