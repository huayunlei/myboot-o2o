package com.ihomefnt.o2o.service.proxy.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.comment.dto.CheckCommentDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentLabelsDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsAddDto;
import com.ihomefnt.o2o.intf.domain.comment.dto.CommentsDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDmsServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.comment.CommentProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;

@Service
public class CommentProxyImpl implements CommentProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;
	
	
	@Override
	public List<CommentLabelsDto> getLabels(Integer type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		try {
			ResponseVo<List<CommentLabelsDto>> response = strongSercviceCaller.post(AladdinDmsServiceNameConstants.API_LABELS, param,
	                new TypeReference<ResponseVo<List<CommentLabelsDto>>>() {
	                });
			
			if (null != response && response.isSuccess()) {
				return response.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}


	@Override
	public CommentsDto getComment(Integer orderId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		try {
			ResponseVo<CommentsDto> response = strongSercviceCaller.post(AladdinDmsServiceNameConstants.API_GETCOMMENT, param,
	                new TypeReference<ResponseVo<CommentsDto>>() {
	                });
			
			if (null != response && response.isSuccess()) {
				return response.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}


	@Override
	public String addComment(CommentsAddDto params) {
		try {
			ResponseVo<?> response = strongSercviceCaller.post(AladdinDmsServiceNameConstants.API_COMMENT, params, ResponseVo.class);
			
			if (null != response && !response.isSuccess()) {
				return response.getMsg();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}


	@Override
	public CheckCommentDto checkCommentByOrderId(Integer orderId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		try {
			ResponseVo<CheckCommentDto> response = strongSercviceCaller.post(AladdinDmsServiceNameConstants.API_COMMENTCHECK, param,
	                new TypeReference<ResponseVo<CheckCommentDto>>() {
	                });
			
			if (null != response && response.isSuccess()) {
				return response.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}
