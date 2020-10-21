/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年8月19日
 * Description:OwnerController.java 
 */
package com.ihomefnt.o2o.api.controller.hbms;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.hbms.vo.request.*;
import com.ihomefnt.o2o.intf.domain.hbms.vo.response.*;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.service.hbms.HbmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhang
 */
@Api(tags = "【交付相关】")
@RequestMapping("/owner")
@RestController
public class OwnerController {

	@Autowired
	private HbmsService hbmsService;

	@ApiOperation(value = "节点是否满意", notes = "测试节点是否满意功能是否正常")
	@PostMapping(value = "/node/confirmNode")
	public ResponseVo<Boolean> confirmNode(@RequestBody ConfirmNodeRequestVo request) {
		if (request == null) {
			return ResponseVo.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		boolean result = hbmsService.confirmNode(request);
		if (!result) {
			return ResponseVo.fail(MessageConstant.FAILED);
		}
		return ResponseVo.success(result);
	}

	@ApiOperation(value = "节点验收项", notes = "测试节点验收项功能是否正常")
	@PostMapping(value = "/node/getNodeItems")
	public ResponseVo<GetNodeItemsReponseVo> getNodeItems(@RequestBody GetNodeItemsRequestVo request) {
		if (request == null) {
			return ResponseVo.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		GetNodeItemsReponseVo result = hbmsService.getNodeItems(request);
		if (result == null) {
			return ResponseVo.fail(MessageConstant.FAILED);
		}
		return ResponseVo.success(result);
	}

	@ApiOperation(value = "施工点评", notes = "测试施工点评功能是否正常")
	@PostMapping(value = "/project/comment")
	public ResponseVo<Boolean> comment(@RequestBody CommentRequestVo request) {
		if (request == null) {
			return ResponseVo.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		boolean result = hbmsService.comment(request);
		if (!result) {
			return ResponseVo.fail(MessageConstant.FAILED);
		}
		return ResponseVo.success(result);
	}

	@ApiOperation(value = "获取施工点评", notes = "测试获取施工点评功能是否正常")
	@PostMapping(value = "/project/getComment")
	public ResponseVo<GetCommentResponseVo> getComment(@RequestBody GetCommentRequestVo request) {
		if (request == null) {
			return ResponseVo.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		GetCommentResponseVo result = hbmsService.getComment(request);
		if (result == null) {
			return ResponseVo.fail(MessageConstant.FAILED);
		}
		return ResponseVo.success(result);
	}

	@ApiOperation(value = "获取项目工艺流程", notes = "测试获取项目工艺流程功能是否正常")
	@PostMapping(value = "/project/getProjectCraft")
	public ResponseVo<List<GetSurveyorProjectNodeResponseVo>> getProjectCraft(@RequestBody OwnerParamRequestVo request) {
		if (request == null) {
			return ResponseVo.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		List<GetSurveyorProjectNodeResponseVo> result = hbmsService.getProjectCraft(request);
		if (result == null) {
			return ResponseVo.fail(MessageConstant.FAILED);
		}
		return ResponseVo.success(result);
	}

	@ApiOperation(value = "项目详情", notes = "测试项目详情功能是否正常")
	@PostMapping(value = "/project/getUnhandleProject")
	public ResponseVo<GetUnhandleProjectResultResponseVo> getUnhandleProject(@RequestBody OwnerParamRequestVo request) {
		if (request == null) {
			return ResponseVo.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		GetUnhandleProjectResultResponseVo result = hbmsService.getUnhandleProject(request);
		if (result == null) {
			return ResponseVo.fail(MessageConstant.FAILED);
		}
		return ResponseVo.success(result);
	}

	@ApiOperation(value = "查询未确认的工期变更", notes = "查询未确认的工期变更")
	@PostMapping(value = "/v5/getUnhandledTimeChange")
	public HttpBaseResponse<TimeChangeResponseVo> getUnhandledTimeChange(@RequestBody TimeChangeRequestVo request) {
		if (request == null || request.getOrderId() == null) {
			return HttpBaseResponse.fail(MessageConstant.PARAMS_NOT_EXISTS);
		}

        TimeChangeResponseVo response = hbmsService.queryNeedConfirmItem(request.getOrderId());
        return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "确认工期变更", notes = "确认工期变更")
	@PostMapping(value = "/v5/confirmTimeChange")
	public HttpBaseResponse<Integer> confirmTimeChange(@RequestBody TimeChangeRequestVo request) {
		if (request == null || request.getOrderId() == null) {
			return HttpBaseResponse.fail(MessageConstant.FAILED);
		}

		Integer result = hbmsService.confirmTimeChange(request.getOrderId());
		if (result != 1) {
			return HttpBaseResponse.fail(MessageConstant.FAILED);
		}
		return HttpBaseResponse.success(result);
	}

}
