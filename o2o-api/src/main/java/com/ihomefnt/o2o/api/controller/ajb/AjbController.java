/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月8日
 * Description:AjbController.java 
 */
package com.ihomefnt.o2o.api.controller.ajb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ihomefnt.o2o.intf.domain.ajb.request.vo.ShareOrderStickyPostRequestVo;
import com.ihomefnt.o2o.intf.service.ajb.AjbService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhang
 */
@Api(tags = "【艾积分API】")
@RestController
@RequestMapping("/ajb")
public class AjbController {

	@Autowired
	private AjbService ajbService;

	@ApiOperation(value = "新家大晒为精华")
	@RequestMapping(value = "/addStickyPost", method = RequestMethod.POST)
	public HttpBaseResponse<Void> addStickyPost(@RequestBody ShareOrderStickyPostRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.AGENT_FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		ajbService.addStickyPost(request.getShareOrderId(), request.getAccessToken());
		return HttpBaseResponse.success();
	}

}
