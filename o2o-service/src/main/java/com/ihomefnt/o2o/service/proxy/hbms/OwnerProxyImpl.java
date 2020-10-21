/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年8月21日
 * Description:OwnerProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.hbms;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.hbms.dto.CommentParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.ConfirmNodeParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetCommentParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetCommentResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetNodeItemsParamDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetNodeItemsResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetSurveyorProjectNodeDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.GetUnhandleProjectResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.NeedConfirmItemsDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.OwnerParamDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinDmsServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.HbmsServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.hbms.OwnerProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;

/**
 * @author zhang
 * @see http://192.168.1.12:8100/hbms/swagger/
 */
@Service
public class OwnerProxyImpl implements OwnerProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public boolean confirmNode(ConfirmNodeParamDto param) {
		try {
			ResponseVo<?> responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.CONFIRM_NODE, param, ResponseVo.class);
			if (responseVo == null) {
				return false;
			}
			return responseVo.isSuccess();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public GetNodeItemsResultDto getNodeItems(GetNodeItemsParamDto param) {
		try {
			ResponseVo<GetNodeItemsResultDto> responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_NODE_ITEMS, param, 
					new TypeReference<ResponseVo<GetNodeItemsResultDto>>() {});
			if (responseVo == null) {
				return null;
			}
			if (responseVo.isSuccess()) {
				return responseVo.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public boolean comment(CommentParamDto param) {
		try {
			ResponseVo<?> responseVo = strongSercviceCaller.post(AladdinDmsServiceNameConstants.OWNER_COMMENT, param, ResponseVo.class);
			if (responseVo == null) {
				return false;
			}
			return responseVo.isSuccess();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public GetCommentResultDto getComment(GetCommentParamDto param) {
		try {
			ResponseVo<GetCommentResultDto> responseVo = strongSercviceCaller.post(AladdinDmsServiceNameConstants.OWNER_GET_COMMENT, param, 
					new TypeReference<ResponseVo<GetCommentResultDto>>() {});
			if (responseVo == null) {
				return null;
			}
			if (responseVo.isSuccess()) {
				return responseVo.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public List<GetSurveyorProjectNodeDto> getProjectCraft(OwnerParamDto param) {
		try {
			ResponseVo<List<GetSurveyorProjectNodeDto>> responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_PROJECT_CRAFT, param, 
					new TypeReference<ResponseVo<List<GetSurveyorProjectNodeDto>>>() {});
			if (responseVo == null) {
				return null;
			}
			if (responseVo.isSuccess()) {
				return responseVo.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public GetUnhandleProjectResultDto getUnhandleProject(OwnerParamDto param) {
		try {
			ResponseVo<GetUnhandleProjectResultDto> responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.GET_UNHANDLE_PROJECT, param, 
					new TypeReference<ResponseVo<GetUnhandleProjectResultDto>>() {});
			if (responseVo == null) {
				return null;
			}
			if (responseVo.isSuccess()) {
				return responseVo.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public List<NeedConfirmItemsDto> queryNeedConfirmItem(Integer orderId) {
		JSONObject param = new JSONObject();
		param.put("orderId",orderId);
		
		try {
			ResponseVo<List<NeedConfirmItemsDto>> responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.QUERY_NEED_CONFIRM_BY_ORDER_ID, param, 
					new TypeReference<ResponseVo<List<NeedConfirmItemsDto>>>() {});
			if (responseVo == null) {
				return null;
			}
			if (responseVo.isSuccess()) {
				return responseVo.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public Integer confirmTimeChange(Integer orderId) {
		JSONObject param = new JSONObject();
		param.put("orderId",orderId);
		
		try {
			ResponseVo<?> responseVo = strongSercviceCaller.post(HbmsServiceNameConstants.CONFIRM_PROJECT_CUSTOM_ITEM, param, ResponseVo.class);
			if (responseVo == null) {
				return 2;
			}
			return responseVo.getCode();
		} catch (Exception e) {
			return null;
		}
	}

}
