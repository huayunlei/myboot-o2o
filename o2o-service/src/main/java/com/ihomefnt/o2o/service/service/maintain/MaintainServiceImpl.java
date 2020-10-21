/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月26日
 * Description:MaintainServiceImpl.java 
 */
package com.ihomefnt.o2o.service.service.maintain;

import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.HttpUserInfoRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskCommentDto;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskCreateUpdateDto;
import com.ihomefnt.o2o.intf.domain.maintain.dto.TaskDetailDto;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainJudgeRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainServiceEvaluationRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainTaskDetailRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainTaskRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainAddTaskResponseVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainOrderInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainTaskDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainUserInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinCustomerInfoVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinHouseInfoResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.maintain.MaintainStatusEnum;
import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.maintain.MaintainProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.intf.service.maintain.MaintainService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang
 */
@Service
public class MaintainServiceImpl implements MaintainService {

	@Autowired
	private ProductProgramProxy productProgramProxy;
	@Autowired
	private MaintainProxy maintainProxy;
	@Autowired
	private UserProxy userProxy;
	@Autowired
	private HouseService houseService;

	@Override
	public MaintainUserInfoResponseVo getMaintainUserInfo(HttpBaseRequest request) {
		if (null == request){
    		throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
    	}
		HttpUserInfoRequest user = request.getUserInfo();
		if(user == null) {
			throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}
		
		MaintainUserInfoResponseVo maintainUserInfoResponse = new MaintainUserInfoResponseVo();
		List<MaintainOrderInfoResponseVo> maintainList = new ArrayList<MaintainOrderInfoResponseVo>();
		maintainUserInfoResponse.setMaintainList(maintainList);
		List<HouseInfoResponseVo> houseInfoList = houseService.queryUserHouseList(user.getId());
		if (CollectionUtils.isNotEmpty(houseInfoList)) {
			maintainUserInfoResponse.setUserId(user.getId());
			int i = 0;
			for (HouseInfoResponseVo houseInfoResponseVo : houseInfoList) {
				Integer masterOrderStatus = houseInfoResponseVo.getMasterOrderStatus();
				// 交付阶段和已完成阶段的用户可以进入极速报修页面
				if (masterOrderStatus != null
						&& (masterOrderStatus == OrderConstant.ORDER_OMSSTATUS_FINISH || masterOrderStatus == OrderConstant.ORDER_OMSSTATUS_DELIVERY)) {
					MaintainOrderInfoResponseVo maintainOrderInfoResponse = new MaintainOrderInfoResponseVo();
					Integer orderId = houseInfoResponseVo.getMasterOrderId();
					maintainOrderInfoResponse.setOrderId(orderId);
					String houseProjectName = houseInfoResponseVo.getPartitionName();// 楼盘分区名称
					String maintainAddress = "";
					if (StringUtils.isNotBlank(houseProjectName)) {
						houseProjectName = houseProjectName.replace(ProductProgramPraise.HOUSE_NAME_BBC, "");
						maintainAddress += houseProjectName;
					}
					String housingNum = houseInfoResponseVo.getHousingNum();// 楼栋号
					if (StringUtils.isNotBlank(housingNum)) {
						maintainAddress += housingNum + "栋";
					}
					String roomNum = houseInfoResponseVo.getRoomNum();// 房间号
					if (StringUtils.isNotBlank(roomNum)) {
						maintainAddress += roomNum + "室";
					}
					maintainOrderInfoResponse.setMaintainAddress(maintainAddress);
					// MOBILE-4611 增加和首页房产列表相同的simpleMaintainAddress字段和小区名称buildingName
					String simpleMaintainAddress = "";
					if(StringUtils.isNotBlank(houseInfoResponseVo.getHousingNum())){
						simpleMaintainAddress += houseInfoResponseVo.getHousingNum();
					}
					if(StringUtils.isNotBlank(houseInfoResponseVo.getUnitNum())){
						if(StringUtils.isNotBlank(simpleMaintainAddress)){
							simpleMaintainAddress += "-";
						}
						simpleMaintainAddress += houseInfoResponseVo.getUnitNum();
					}
					if(StringUtils.isNotBlank(houseInfoResponseVo.getRoomNum())){
						if(StringUtils.isNotBlank(simpleMaintainAddress)){
							simpleMaintainAddress += "-";
						}
						simpleMaintainAddress += houseInfoResponseVo.getRoomNum();
					}
					maintainOrderInfoResponse.setSimpleMaintainAddress(simpleMaintainAddress);
					maintainOrderInfoResponse.setBuildingName(houseInfoResponseVo.getHouseProjectName().replace(ProductProgramPraise.HOUSE_NAME_BBC, ""));
					maintainList.add(maintainOrderInfoResponse);
					// 只查询一次,性能应该还好
					if (i == 0) {
						Integer houseId = houseInfoResponseVo.getId();
						AladdinHouseInfoResponseVo aladdinHouseInfoResponseVo = houseService.queryHouseByHouseId(houseId);
						if (aladdinHouseInfoResponseVo != null) {
							AladdinCustomerInfoVo userInfo = aladdinHouseInfoResponseVo.getUserInfo();
							if (userInfo != null) {
								String name = userInfo.getName();
								if (StringUtils.isNotBlank(name)) {
									maintainUserInfoResponse.setLinkMan(name);
								}
								String mobile = userInfo.getMobile();
								if (StringUtils.isNotBlank(mobile)) {
									maintainUserInfoResponse.setLinkMobile(mobile);
								}
							}
						}
					}
					i++;
				}
			}
		}
		return maintainUserInfoResponse;
	}

	@Override
	public Integer addTask(MaintainTaskRequestVo request) {
		if (request.getOrderId() == null){
			throw new BusinessException(MessageConstant.PARAMS_NOT_EXISTS);
		}
		
		if(StringUtils.isEmpty(request.getQuestionDesc())){
			throw new BusinessException("请简单描述您的问题~");
		}
		
		if(CollectionUtils.isEmpty(request.getMaintainUrls())){
			throw new BusinessException("至少上传1张图片我们才能处理哦~");
		}

		if(CollectionUtils.isNotEmpty(request.getMaintainUrls()) && request.getMaintainUrls().size() > 9){
			throw new BusinessException("最多只能上传9张图片哦~");
		}
		
		if(StringUtils.isEmpty(request.getLinkMobile())) {
			throw new BusinessException("没有电话我们可联系不到您呀~");
		} 
				
		Integer orderId = request.getOrderId();
		if (orderId != null && orderId > 0) {
			TaskCreateUpdateDto vo = new TaskCreateUpdateDto();
			vo.setLinkmanMobile(request.getLinkMobile());
			vo.setLinkmanName(request.getLinkMan());
			vo.setOrderId(request.getOrderId());
			vo.setTaskDesc(request.getQuestionDesc());
			vo.setPictureUrls(request.getMaintainUrls());
			Integer taskId = maintainProxy.addTask(vo);
			if (taskId == null) {
				throw new BusinessException(MessageConstant.FAILED);
			}
			return taskId;
		}
		return null;
	}

	@Override
	public PageModel<MaintainTaskDetailResponseVo> queryMaintainRecordList(MaintainJudgeRequestVo request) {
		if (null == request || StringUtils.isBlank(request.getAccessToken())){
    		throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
    	}
		HttpUserInfoRequest user = request.getUserInfo();
		if(user == null) {
			throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}
		
		PageModel<MaintainTaskDetailResponseVo> pageModel = new PageModel<MaintainTaskDetailResponseVo>();
		List<MaintainTaskDetailResponseVo> taskDetailList = new ArrayList<MaintainTaskDetailResponseVo>();
		
		List<TaskDetailDto> detailResponseVos = maintainProxy.queryList(user.getId(), null, request.getOrderId());
		if(CollectionUtils.isNotEmpty(detailResponseVos)){
			for (TaskDetailDto taskDetailResponseVo : detailResponseVos) {
				MaintainTaskDetailResponseVo response = new MaintainTaskDetailResponseVo();
				if(StringUtils.isNotBlank(taskDetailResponseVo.getAddress())){
					response.setMaintainAddress(taskDetailResponseVo.getAddress());
				}
				if(StringUtils.isNotBlank(taskDetailResponseVo.getCreateTime())){
					response.setMaintainTime(taskDetailResponseVo.getCreateTime());
				}
				response.setServiceMobile(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);
				response.setTaskId(taskDetailResponseVo.getId());
				MaintainStatusEnum maintainStatusEnum = MaintainStatusEnum.getMiantainStatusEnum(taskDetailResponseVo.getStatus(), taskDetailResponseVo.getCommentStatus());
				if(maintainStatusEnum != null){
					response.setTaskStatus(maintainStatusEnum.getStatus());
					response.setTaskStatusDesc(maintainStatusEnum.getStatusDesc());
					response.setNextTaskStatusDesc(maintainStatusEnum.getNextStatusDesc());
					response.setPraiseDesc(maintainStatusEnum.getPraiseDesc());
				}
				if(taskDetailResponseVo.getOrderId() != null){
					response.setOrderId(taskDetailResponseVo.getOrderId());
				}
				if(StringUtils.isNotBlank(taskDetailResponseVo.getAppointDate())){
					response.setSubscribeTime(taskDetailResponseVo.getAppointDate());
				}
				taskDetailList.add(response);
			}
		}
		
		pageModel.setList(taskDetailList);
		pageModel.setPageNo(1);
		pageModel.setPageSize(taskDetailList.size());
		pageModel.setTotalPages(1);
		pageModel.setTotalRecords(taskDetailList.size());
		
		return pageModel;
	}

	@Override
	public MaintainTaskDetailResponseVo queryMaintainRecordDetail(MaintainTaskDetailRequestVo request) {
		if (StringUtils.isBlank(request.getAccessToken()) || request.getTaskId() == null) {
    		throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		MaintainTaskDetailResponseVo response = new MaintainTaskDetailResponseVo();
		Integer width = 0;
		if(request.getWidth() != null){
			width = request.getWidth();
		}
		
		TaskDetailDto detailResponseVo = maintainProxy.queryDetail(request.getTaskId());
		if(detailResponseVo != null){
			if(StringUtils.isNotBlank(detailResponseVo.getCancelDesc())){
				response.setCancelDesc(detailResponseVo.getCancelDesc());
			}
			if(StringUtils.isNotBlank(detailResponseVo.getCancelName())){
				response.setCancelMan(detailResponseVo.getCancelName());
			}
			if(StringUtils.isNotBlank(detailResponseVo.getLinkmanName())){
				response.setLinkMan(detailResponseVo.getLinkmanName());
			}
			if(StringUtils.isNotBlank(detailResponseVo.getLinkmanMobile())){
				response.setHideLinkMobile(detailResponseVo.getLinkmanMobile().replaceAll(ProductProgramPraise.MOBILE_REGEX, ProductProgramPraise.MOBILE_REPLACE));
				response.setLinkMobile(detailResponseVo.getLinkmanMobile());
			}
			if(StringUtils.isNotBlank(detailResponseVo.getAddress())){
				response.setMaintainAddress(detailResponseVo.getAddress());
			}
			if(StringUtils.isNotBlank(detailResponseVo.getCreateTime())){
				response.setMaintainTime(detailResponseVo.getCreateTime());
			}
			List<String> maintainUrls = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(detailResponseVo.getPictureUrls())){
				for (String picUrl : detailResponseVo.getPictureUrls()) {
					if(StringUtils.isNotBlank(picUrl)){
						if(picUrl.contains("?imageView")){
							maintainUrls.add(picUrl);
						}else{
							maintainUrls.add(QiniuImageUtils.compressImageAndSamePicTwo(picUrl, width, -1));
						}
					}
				}
			}
			response.setMaintainUrls(maintainUrls);
			if(StringUtils.isNotBlank(detailResponseVo.getClientDesc())){
				response.setQuestionDesc(detailResponseVo.getClientDesc());
			}
			response.setServiceMobile(ProductProgramPraise.ADVISER_MOBILE_DEFAULT);
			if(StringUtils.isNotBlank(detailResponseVo.getAppointDate())){
				response.setSubscribeTime(detailResponseVo.getAppointDate());
			}
			response.setTaskId(detailResponseVo.getId());
			MaintainStatusEnum maintainStatusEnum = MaintainStatusEnum.getMiantainStatusEnum(detailResponseVo.getStatus(), detailResponseVo.getCommentStatus());
			if(maintainStatusEnum != null){
				response.setTaskStatus(maintainStatusEnum.getStatus());
				response.setTaskStatusDesc(maintainStatusEnum.getStatusDesc());
				response.setNextTaskStatusDesc(maintainStatusEnum.getNextStatusDesc());
				response.setPraiseDesc(maintainStatusEnum.getPraiseDesc());
			}
			if(detailResponseVo.getOrderId() != null){
				response.setOrderId(detailResponseVo.getOrderId());
			}
		}
		
		return response;
	}

	@Override
	public Boolean addServiceEvaluation(MaintainServiceEvaluationRequestVo request) {
		if (StringUtils.isBlank(request.getAccessToken()) || request.getTaskId() == null) {
			throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		if (request.getTaskId() == null || request.getOverallSatisfactionStar() == null){
			throw new BusinessException(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		TaskCommentDto vo = new TaskCommentDto();
		if(StringUtils.isNotBlank(request.getEvaluationContent())){
			vo.setContent(request.getEvaluationContent());
		}
		vo.setId(request.getTaskId());
		if(request.getMaintainQualityStar() != null){
			vo.setQualityScore(request.getMaintainQualityStar());
		}
		if(request.getMaintainServiceStar() != null){
			vo.setRepairScore(request.getMaintainServiceStar());
		}
		if(request.getMaintainTimeStar() != null){
			vo.setScheduleScore(request.getMaintainTimeStar());
		}
		if(request.getOverallSatisfactionStar() != null){
			vo.setScore(request.getOverallSatisfactionStar());
		}
		if(request.getCustomerServiceStar() != null){
			vo.setServiceScore(request.getCustomerServiceStar());
		}
		
		return maintainProxy.comment(vo);
	}

	@Override
	public Integer updateTask(MaintainTaskRequestVo request) {
		if (request.getTaskId() == null){
			throw new BusinessException(MessageConstant.PARAMS_NOT_EXISTS);
		}
		
		if(StringUtils.isEmpty(request.getQuestionDesc())){
			throw new BusinessException("请简单描述您的问题~");
		}
		
		if(CollectionUtils.isEmpty(request.getMaintainUrls())){
			throw new BusinessException("至少上传1张图片我们才能处理哦~");
		}
		if(CollectionUtils.isNotEmpty(request.getMaintainUrls()) && request.getMaintainUrls().size() > 9){
			throw new BusinessException("最多只能上传9张图片哦~");
		}

		if(StringUtils.isEmpty(request.getLinkMobile())) {
			throw new BusinessException("没有电话我们可联系不到您呀~");
		} 
		
		Integer taskId = request.getTaskId();
		if (taskId != null && taskId > 0) {
			TaskCreateUpdateDto vo = new TaskCreateUpdateDto();
			vo.setLinkmanMobile(request.getLinkMobile());
			vo.setLinkmanName(request.getLinkMan());
			vo.setTaskDesc(request.getQuestionDesc());
			vo.setPictureUrls(request.getMaintainUrls());
			vo.setId(taskId);
			vo.setOrderId(request.getOrderId());
			if(!maintainProxy.updateTask(vo)){
				throw new BusinessException(MessageConstant.FAILED);
			}
		}
		return taskId;
	}

	@Override
	public Integer cancelTask(Integer taskId) {
		if (taskId == null || taskId == 0){
			throw new BusinessException(MessageConstant.PARAMS_NOT_EXISTS);
		}
		
		if(!maintainProxy.cancelTask(taskId)){
			throw new BusinessException(MessageConstant.FAILED);
		}
			
		return taskId;
	}

	@Override
	public Boolean judgeInProcess(MaintainJudgeRequestVo request) {
		if (null == request){
    		throw new BusinessException(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
    	}
		HttpUserInfoRequest user = request.getUserInfo();
		if(user == null) {
			throw new BusinessException(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}
		
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(MaintainStatusEnum.MAINTAIN_STATUS_ONALLOCATE.getCode());
		statusList.add(MaintainStatusEnum.MAINTAIN_STATUS_ONRESERVE.getCode());
		statusList.add(MaintainStatusEnum.MAINTAIN_STATUS_RESERVED.getCode());
		statusList.add(MaintainStatusEnum.MAINTAIN_STATUS_ONVISIT.getCode());
		statusList.add(MaintainStatusEnum.MAINTAIN_STATUS_VISITED.getCode());
		statusList.add(MaintainStatusEnum.MAINTAIN_STATUS_PROCESSED.getCode());
		List<TaskDetailDto> detailResponseVos = maintainProxy.queryList(user.getId(), statusList, request.getOrderId());
		if(CollectionUtils.isNotEmpty(detailResponseVos)){
			return true;
		}
		return false;
	}

	@Override
	public MaintainAddTaskResponseVo maintainAddTask(MaintainTaskRequestVo request) {
		MaintainAddTaskResponseVo response = new MaintainAddTaskResponseVo();
		Integer taskId = null;
		if(request.getTaskType() != null && request.getTaskType().equals(1)){
			taskId = this.updateTask(request);
		}else if(request.getTaskType() != null && request.getTaskType().equals(2)){
			taskId = this.cancelTask(request.getTaskId());
		}else{
			taskId = this.addTask(request);
		}
		response.setTaskId(taskId);
		return response;
	}

}
