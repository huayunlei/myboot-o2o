package com.ihomefnt.o2o.service.proxy.meeting;

import com.ihomefnt.o2o.intf.domain.meeting.dto.FamilyGroupDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.HouseManagerDto;
import com.ihomefnt.o2o.intf.domain.meeting.dto.PicWallListDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.meeting.MeetingPicProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 照片墙
 * @author ZHAO
 */
@Service
public class MeetingPicProxyImpl implements MeetingPicProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public HouseManagerDto searchHouseManager(Map<String, Object> params) {
		HttpBaseResponse<HouseManagerDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_PIC_SEARCH_HOUSE_MANAGER, params,
					new TypeReference<HttpBaseResponse<HouseManagerDto>>() {
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
	public boolean publishPic(Map<String, Object> params) {
		HttpBaseResponse<?> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_PIC_PUBLISH_PIC, params, HttpBaseResponse.class);
		} catch (Exception e) {
			return false;
		}
		if(responseVo == null || responseVo.getObj() == null){
			return false;
		}
		return true;
	}

	@Override
	public PicWallListDto searchPicWall(Map<String, Object> params) {
		HttpBaseResponse<PicWallListDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_PIC_SEARCH_PIC_WALL, params,
					new TypeReference<HttpBaseResponse<PicWallListDto>>() {
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
	public FamilyGroupDto searchFamilyGroup() {
		Map<String, Object> params = new HashMap<String, Object>();
		HttpBaseResponse<FamilyGroupDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_PIC_SEARCH_FAMILY_GROUP, params,
					new TypeReference<HttpBaseResponse<FamilyGroupDto>>() {
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
	public String uploadImage(Map<String, Object> params) {
		HttpBaseResponse<String> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_PIC_UPLOAD_IMAGE, params,
					new TypeReference<HttpBaseResponse<String>>() {
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
	public String uploadImageBase64(Map<String, Object> params) {
		HttpBaseResponse<String> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.MEETING_PIC_UPLOAD_IMAGE_BASE64, params,
					new TypeReference<HttpBaseResponse<String>>() {
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
