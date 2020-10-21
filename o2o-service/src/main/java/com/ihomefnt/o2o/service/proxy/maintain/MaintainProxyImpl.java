/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年2月1日
 * Description:MaintainProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.maintain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskCommentDto;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskCreateUpdateDto;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskDetailDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinCscServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.maintain.MaintainProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;

/**
 * @author zhang
 */
@Service
public class MaintainProxyImpl implements MaintainProxy {

	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public Integer addTask(TaskCreateUpdateDto paramMap) {
		try {
			ResponseVo<Integer> response = strongSercviceCaller.post(AladdinCscServiceNameConstants.REPAIR_TASK_CREATE, paramMap,
					new TypeReference<ResponseVo<Integer>>() {
			});
			
			if (null != response && response.isSuccess() && response.getData() != null) {
				return response.getData();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public boolean updateTask(TaskCreateUpdateDto paramMap) {
		try {
			ResponseVo<?> response = strongSercviceCaller.post(AladdinCscServiceNameConstants.REPAIR_TASK_UPDATE, paramMap, ResponseVo.class);
			
			if (null != response && response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public boolean cancelTask(Integer id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		try {
			ResponseVo<?> response = strongSercviceCaller.post(AladdinCscServiceNameConstants.REPAIR_TASK_CANCEL, paramMap, ResponseVo.class);
			
			if (null != response && response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public TaskDetailDto queryDetail(Integer id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		try {
			ResponseVo<TaskDetailDto> response = strongSercviceCaller.post(AladdinCscServiceNameConstants.REPAIR_TASK_QUERY_DETAIL, paramMap,
					new TypeReference<ResponseVo<TaskDetailDto>>() {
			});
			
			if (null != response && response.isSuccess() && response.getData() != null) {
				return response.getData();
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}

	@Override
	public List<TaskDetailDto> queryList(Integer userId, List<Integer> statusList, Integer orderId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		if(CollectionUtils.isNotEmpty(statusList)){
			paramMap.put("statusList", statusList);
		}
		if(orderId != null){
			paramMap.put("orderId", orderId);
		}
		
		try {
			ResponseVo<List<TaskDetailDto>> response = strongSercviceCaller.post(AladdinCscServiceNameConstants.REPAIR_TASK_QUERY_LIST, paramMap,
					new TypeReference<ResponseVo<List<TaskDetailDto>>>() {
			});
			
			if (null != response && response.isSuccess() && response.getData() != null) {
				return response.getData();
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}

	@Override
	public boolean comment(TaskCommentDto paramMap) {
		try {
			ResponseVo<?> response = strongSercviceCaller.post(AladdinCscServiceNameConstants.REPAIR_TASK_COMMENT, paramMap, ResponseVo.class);
			
			if (null != response && response.isSuccess()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}

}
