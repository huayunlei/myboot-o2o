/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月8日
 * Description:ArtCommentController.java 
 */
package com.ihomefnt.o2o.api.controller.artcomment;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.common.util.validation.ValidationResult;
import com.ihomefnt.common.util.validation.ValidationUtils;
import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentDto;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentCreateRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentListRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentViewRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.response.ArtCommentPageListResponse;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.response.ArtCommentResponse;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.response.CreateArtCommentResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.order.dto.OrderDtoVo;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.artcomment.ArtCommentConstant;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.service.artcomment.ArtCommentService;
import com.ihomefnt.o2o.intf.service.order.OrderService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 艺术品评价控制器
 * 
 * @author zhang
 */
@ApiIgnore
@Deprecated
@RestController
@Api(tags = "【艺术品评价API】",hidden = true)
@RequestMapping("/artComment")
public class ArtCommentController {
	@Autowired
    OrderProxy orderProxy;
	@Autowired
	OrderService orderService;
	@Autowired
	ArtCommentService artCommentService;
	@Autowired
	UserService userService;

	private static final Logger LOG = LoggerFactory.getLogger(ArtCommentController.class);

	@ApiOperation(value = "发表评价", notes = "测试发表评价是否正常")
	@RequestMapping(value = "/createArtComment", method = RequestMethod.POST)
	public HttpBaseResponse<CreateArtCommentResponseVo> createArtComment(@RequestBody ArtCommentCreateRequest request) {
		/**
		 * 验证前台传入参数是否合法
		 */
		String msg = checkCommonFailed(request);
		if (null != msg) {
			return HttpBaseResponse.fail(msg);
		}
		/**
		 * 验证是否登陆
		 */
		String accessToken = request.getAccessToken();
		UserDto userDto = userService.getUserByToken(accessToken);
		if (userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.ADMIN_ILLEGAL, MessageConstant.ADMIN_ILLEGAL);
		}
		
		/**
		 * 当发表到晒家时,图片不能为空
		 */
		Boolean toShareOrderTag = request.getToShareOrderTag();
		if (toShareOrderTag) {
			List<String> images = request.getImages();
			if (CollectionUtils.isEmpty(images)) {
				return HttpBaseResponse.fail(ArtCommentConstant.DATA_IMAGES_EMPTY);
			}
		}
		/**
		 * 验证用户和订单关系,防止恶意刷评
		 */
		Integer orderId = request.getOrderId();
		OrderDtoVo orderDto = null;
		try {
			orderDto = orderService.queryOrderDetail(orderId);
		} catch (Exception e) {
			LOG.error("omsOrderService o2o-exception , more info :{}", e);
		}
		if (orderDto == null) {
			return HttpBaseResponse.fail(ArtCommentConstant.DATA_IMAGES_EMPTY);
		}
		Integer fidUser = orderDto.getFidUser();
		Integer userId = userDto.getId();
		boolean userResult = (fidUser != null && userId != null && fidUser.intValue() == userId.intValue());
		if (!userResult) {
			return HttpBaseResponse.fail(MessageConstant.ILLEGAL_USER);
		}
		ArtCommentDto dto = ModelMapperUtil.strictMap(request, ArtCommentDto.class);
		dto.setUserId(userId);
		//订单编号
		dto.setOrderNum(orderDto.getOrderNum());
		//Member member = userDto.getMember();
		MemberDto member = userDto.getMember();
		if (member != null) {
			dto.setNickName(member.getNickName());
			dto.setNickImg(member.getuImg());
		}
		
		List<ArtCommentDto> oldComment = artCommentService.getCommentListByOrderIdOrderTypeProdId(orderId,
				request.getProductId(), request.getOrderType());
		if (CollectionUtils.isNotEmpty(oldComment)) {
			return HttpBaseResponse.fail(ArtCommentConstant.CREATE_COMMENT_ERROR);
		}
		String id = artCommentService.createArtComment(dto);
		if (StringUtils.isBlank(id)) {
			return HttpBaseResponse.fail(ArtCommentConstant.CREATE_COMMENT_ERROR);
		}
		
		return HttpBaseResponse.success(new CreateArtCommentResponseVo(id));
	}

	@ApiOperation(value = "查看评价详情", notes = "测试查看评价详情是否正常")
	@RequestMapping(value = "/viewArtComment", method = RequestMethod.POST)
	public HttpBaseResponse<ArtCommentResponse> viewArtComment(@RequestBody ArtCommentViewRequest request) {
		/**
		 * 验证前台传入参数是否合法
		 */
		String msg = checkCommonFailed(request);
		if (null != msg) {
			return HttpBaseResponse.fail(msg);
		}
		
		ArtCommentResponse response = artCommentService.viewArtCommentByPK(request);
		if (response == null) {
			return HttpBaseResponse.fail(MessageConstant.FAILED);
		}
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "查看评价列表", notes = "测试查看评价列表是否正常")
	@RequestMapping(value = "/listArtComment", method = RequestMethod.POST)
	public HttpBaseResponse<ArtCommentPageListResponse> listArtComment(@RequestBody ArtCommentListRequest request) {
		/**
		 * 验证前台传入参数是否合法
		 */
		String msg = checkCommonFailed(request);
		if (null != msg) {
			return HttpBaseResponse.fail(msg);
		}
		
		ArtCommentPageListResponse response = artCommentService.listArtCommentByCondition(request);
		return HttpBaseResponse.success(response);
	}

	/**
	 * 验证前台传入参数是否失败 :true 失败,false成功
	 * 
	 * @param request
	 * @param message
	 * @return
	 */
	private String checkCommonFailed(Object request) {
		ValidationResult result = ValidationUtils.validateEntity(request);
		if (result.isHasErrors()) {
			Map<String, String> map = result.getErrorMsg();
			StringBuffer buff = new StringBuffer();
			int index = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				index++;
				buff.append(entry.getValue());
				if (index < map.size()) {
					buff.append(",");
				}
			}
			return buff.toString();
		}
		return null;
	}

	@ApiOperation(value = "艺术品评论表数据整理", notes = "特定人员使用")
	@RequestMapping(value = "/brushArtData", method = RequestMethod.POST)
	public HttpBaseResponse<String> brushArtData(@RequestBody HttpBaseRequest request) {
		String response = artCommentService.brushArtData();
		return HttpBaseResponse.success(response);
	}
	
	@ApiOperation(value = "将评价数据从mongodb刷新到redis", notes = "特定人员使用")
	@RequestMapping(value = "/synCommentDataListFromMongoDBToRedis", method = RequestMethod.POST)
	public HttpBaseResponse<Void> synCommentDataListFromMongoDBToRedis(@RequestBody HttpBaseRequest request) {
		artCommentService.synCommentDataListFromMongoDBToRedis();
		return HttpBaseResponse.success();
	}

}
