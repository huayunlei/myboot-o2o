/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月26日
 * Description:MaintainController.java 
 */
package com.ihomefnt.o2o.api.controller.maintain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.agent.dto.PageModel;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainJudgeRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainServiceEvaluationRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainTaskDetailRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.request.MaintainTaskRequestVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainAddTaskResponseVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainTaskDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.maintain.vo.response.MaintainUserInfoResponseVo;
import com.ihomefnt.o2o.intf.service.maintain.MaintainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 保修
 * 
 * @author zhang
 * @see http://wiki.ihomefnt.com:8002/pages/viewpage.action?pageId=14909451
 */
@Api(tags = "【极速报修API】")
@RequestMapping("/maintain")
@RestController
public class MaintainController {

	@Autowired
	private MaintainService maintainService;

	@ApiOperation(value = "查询报修的用户信息", notes = "首页卡片(不提供,首页卡片已有)、我的(提供)、全品家订单(不提供,订单已有)")
	@RequestMapping(value = "/getMaintainUserInfo", method = RequestMethod.POST)
	public HttpBaseResponse<MaintainUserInfoResponseVo> getMaintainUserInfo(@RequestBody HttpBaseRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		MaintainUserInfoResponseVo result = maintainService.getMaintainUserInfo(request);
		return HttpBaseResponse.success(result);
	}

	@ApiOperation(value = "新增、更新、取消报修记录", notes = "维修图片最多三张")
	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	public HttpBaseResponse<MaintainAddTaskResponseVo> addTask(@RequestBody MaintainTaskRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		MaintainAddTaskResponseVo result = maintainService.maintainAddTask(request);
		return HttpBaseResponse.success(result);
	}

	@ApiOperation(value = "查询报修记录列表", notes = "查询报修记录列表")
	@RequestMapping(value = "/queryMaintainRecordList", method = RequestMethod.POST)
	public HttpBaseResponse<PageModel<MaintainTaskDetailResponseVo>> queryMaintainRecordList(@RequestBody MaintainJudgeRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		PageModel<MaintainTaskDetailResponseVo> result = maintainService.queryMaintainRecordList(request);
		return HttpBaseResponse.success(result);
	}

	@ApiOperation(value = "查询报修记录详情", notes = "查询报修记录详情")
	@RequestMapping(value = "/queryMaintainRecordDetail", method = RequestMethod.POST)
	public HttpBaseResponse<MaintainTaskDetailResponseVo> queryMaintainRecordDetail(@RequestBody MaintainTaskDetailRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		MaintainTaskDetailResponseVo result = maintainService.queryMaintainRecordDetail(request);
		return HttpBaseResponse.success(result);
	}
	
	@ApiOperation(value = "新增服务评价", notes = "新增服务评价")
	@RequestMapping(value = "/addServiceEvaluation", method = RequestMethod.POST)
	public HttpBaseResponse<Boolean> addServiceEvaluation(@RequestBody MaintainServiceEvaluationRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		Boolean result = maintainService.addServiceEvaluation(request);
		return HttpBaseResponse.success(result);
	}

	@ApiOperation(value = "判断正在处理中的报修记录", notes = "判断正在处理中的报修记录")
	@RequestMapping(value = "/judgeInProcess", method = RequestMethod.POST)
	public HttpBaseResponse<Boolean> judgeInProcess(@RequestBody MaintainJudgeRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		Boolean result = maintainService.judgeInProcess(request);
		return HttpBaseResponse.success(result);
	}
}
