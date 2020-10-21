package com.ihomefnt.o2o.service.proxy.art;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.art.dto.ArtSubjectListResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.FrameArtResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.FrameCategoryResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.SubjectProductResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.art.ArtCategoryProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;

/**
 * 艺术品品类代理
 * @author ZHAO
 */
@Service
public class ArtCategoryProxyImpl implements ArtCategoryProxy{
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;
	
	@Override
	public FrameArtResponseVo queryFrameCategoryListByFrameId(Integer frameId) {
		JSONObject param = new JSONObject();
		param.put("frameId", frameId);
		HttpBaseResponse<FrameArtResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_LIST_BY_FRAME_ID, param, 
					new TypeReference<HttpBaseResponse<FrameArtResponseVo>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if(responseVo.getObj() != null){
			return responseVo.getObj();
		}
		return null;
	}
	

	@Override
	public FrameArtResponseVo queryListByFrameIdList(List<Integer> frameIdList) {
		JSONObject param = new JSONObject();
		param.put("frameIdList", frameIdList);
		HttpBaseResponse<FrameArtResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_LIST_BY_FRAME_ID_LIST, param, 
					new TypeReference<HttpBaseResponse<FrameArtResponseVo>>() {
			});
		} catch (Exception e) {
			return null;
		}
		if(responseVo.getObj() != null){
			return responseVo.getObj();
		}
		return null;
	}


	@Override
	public FrameCategoryResponseVo queryAllFrameList() {
		JSONObject param = new JSONObject();
		HttpBaseResponse<FrameCategoryResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_ALL_FRAME_LIST, param, 
					new TypeReference<HttpBaseResponse<FrameCategoryResponseVo>>() {
			});
		} catch (Exception e) {
			return null;
		}
		if(responseVo.getObj() != null){
			return responseVo.getObj();
		}
		return null;
	}

	@Override
	public ArtSubjectListResponseVo getArtSubjectList(Integer pageNo, Integer pageSize) {
		JSONObject param = new JSONObject();
		param.put("pageNo", pageNo);
		param.put("pageSize", pageSize);
		HttpBaseResponse<ArtSubjectListResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_SUBJECT_LIST, param, 
					new TypeReference<HttpBaseResponse<ArtSubjectListResponseVo>>() {
			});
		} catch (Exception e) {
			return null;
		}
		if(responseVo.getObj() != null){
			return responseVo.getObj();
		}
		return null;
	}

	@Override
	public SubjectProductResponseVo querySubjectDetailById(Integer subjectId) {
		JSONObject param = new JSONObject();
		param.put("subjectId", subjectId);
		HttpBaseResponse<SubjectProductResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_SUBJECT_BY_ID, param, 
					new TypeReference<HttpBaseResponse<SubjectProductResponseVo>>() {
			});
		} catch (Exception e) {
			return null;
		}
		if(responseVo.getObj() != null){
			return responseVo.getObj();
		}
		return null;
	}


}
