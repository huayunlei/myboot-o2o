package com.ihomefnt.o2o.api.controller.shareorder;

import com.ihomefnt.o2o.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageOrderDetailRequest;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.ShareOrder;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.ShareOrderComment;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.ShareOrderDto;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.ShareOrderCommentListResponseVo;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.ShareOrderDetailResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.UserHouseInfoResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.service.ajb.AjbService;
import com.ihomefnt.o2o.intf.service.shareorder.ShareOrderService;
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

import java.util.ArrayList;
import java.util.List;

//import com.ihomefnt.user.dto.UserDto;


/**
 * Created by onefish on 2016/11/3 0003.
 */
@Api(tags = "【新家大晒API】")
@RestController
@RequestMapping("/shareorder")
public class ShareOrderController {
    private static final Logger LOG = LoggerFactory.getLogger(ShareOrderController.class);


    @Autowired
    private ShareOrderService shareOrderService;
  
	@Autowired
	private AjbService ajbService;

	@Autowired
	UserService userService;

	/**
     * 获取新家大晒列表
     * @param request
     * @return
     */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "获取新家大晒列表")
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public HttpBasePageResponse<ShareOrder> getShareOrderList(@RequestBody HttpShareOrderRequest request) {
		return shareOrderService.getShareOrderList(request);
	}

    /**
     * 点赞
     * @param shareOrderPraiseRequest
     * @return
     */
    @ApiOperation(value = "新家大晒点赞")
    @RequestMapping(value = "/addPraise", method = RequestMethod.POST)
    public HttpBaseResponse<Integer> addPraise(@RequestBody HttpShareOrderPraiseRequest shareOrderPraiseRequest) {
		Integer result = 0;
		if(shareOrderPraiseRequest.getType()==0){
			result = shareOrderService.addPraise(shareOrderPraiseRequest);
			try {
                ajbService.addStickyPost(shareOrderPraiseRequest.getShareOrderId(), shareOrderPraiseRequest.getAccessToken());
            } catch (BusinessException e) {
                LOG.info("ShareOrderController addPraise :{}, BusinessException :{}", JsonUtils.obj2json(shareOrderPraiseRequest), e);
            }
		}else{
			result = shareOrderService.addPraiseByType(shareOrderPraiseRequest,shareOrderPraiseRequest.getType());
		}
		return HttpBaseResponse.success(result);
    }
    
    /**
     * 转发
     * @return
     */
    @ApiOperation(value = "转发:本次只实现type为1,表示专题")
    @RequestMapping(value = "/forward", method = RequestMethod.POST)
    public HttpBaseResponse<Integer> forward(@RequestBody HttpForwardRequest request) {
		if (request == null || request.getType() != 1) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, "其他类型暂不支持");
		}
		Integer result = shareOrderService.forward(request);
		return HttpBaseResponse.success(result);
	}

    /**
     * 发布新家大晒
     * @param shareOrderDto
     * @return
     */
    @ApiOperation(value = "发布新家大晒")
    @RequestMapping(value = "/addShareOrder",method = RequestMethod.POST)
	public HttpBaseResponse<String> addShareOrder(@RequestBody ShareOrderDto shareOrderDto) {
		String result = null;
		if (shareOrderDto != null && shareOrderDto.getType() == 0) {
			result = shareOrderService.addShareOrder(shareOrderDto);
		} else if (shareOrderDto != null && shareOrderDto.getType() == 1) {
			result = shareOrderService.addShareOrderForArticle(shareOrderDto);
		} else {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, "其他类型暂不支持");
		}
		return HttpBaseResponse.success(result);
	}
    
    /**
     * 新家大晒增加评论
     * @param request
     * @return
     */
	@ApiOperation(value = "增加新家大晒评论")
	@RequestMapping(value = "/addShareOrderComment", method = RequestMethod.POST)
	public HttpBaseResponse<String> addShareOrderComment(@RequestBody HttpShareOrderCommentRequest request) {
		if (null == request || null == request.getAccessToken() || null == request.getShareOrderId()
				|| null == request.getComment()) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		int type = request.getType();
		if (type != 0 && type != 1) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, "其他类型暂不支持");
		}
		UserDto user = userService.getUserByToken(request.getAccessToken());
		if (null == user) {
			return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.USER_NOT_LOGIN);
		}

		String result = null;
		// 晒家类型:0 表示老晒家, 1 表示专题
		if (type == 0) {
			// 老晒家:已实名认证
			result = shareOrderService.addShareOrderComment(request);
		} else if (type == 1) {
			//业务需求:为了鼓励用户发表,暂不要求实名认证
			result = shareOrderService.addShareOrderCommentForTopic(request,user);
		}
		return HttpBaseResponse.success(result);
	}
    
    /**
     * 获取单个新晒大家文章评论
     * @param request
     * @return
     */
    @ApiOperation(value="获取单个晒家文章的评论")
    @RequestMapping(value="/getShareOrderCommentList",method = RequestMethod.POST)
    public HttpBaseResponse<ShareOrderCommentListResponseVo> getShareOrderCommentList(@RequestBody HttpShareOrderCommonListRequest request) {
    	if(null == request || StringUtil.isNullOrEmpty(request.getShareOrderId())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		List<ShareOrderComment> shareOrderCommentList = shareOrderService.getShareOrderCommentList(request);
		return HttpBaseResponse.success(new ShareOrderCommentListResponseVo(shareOrderCommentList));
    }
    
    @ApiOperation(value = "删除新家大晒")
    @RequestMapping(value = "/deleteShareOrderById",method = RequestMethod.POST)
    public HttpBaseResponse<Boolean> deleteShareOrderById(@RequestBody ShareOrderDetailRequest request) {
    	if(request == null || StringUtils.isBlank(request.getShareOrderId())){
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
    	}

		HttpUserInfoRequest user = request.getUserInfo();
		if (null == user) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.USER_NOT_LOGIN);
		}
		boolean result = shareOrderService.deleteShareOrderById(request.getShareOrderId());
		return HttpBaseResponse.success(result);
    }

	@ApiOperation(value = "查询晒家详情")
	@RequestMapping(value = "/queryShareOrderDetailById", method = RequestMethod.POST)
	public HttpBaseResponse<ShareOrderDetailResponse> queryShareOrderDetailById(@RequestBody ShareOrderDetailRequest request) {
		if (request == null || StringUtils.isBlank(request.getShareOrderId())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		HttpUserInfoRequest user = request.getUserInfo();

		ShareOrderDetailResponse result = null;
		// 1:表示楼盘运营: 不需要登录  0:表示老晒家
		if (request.getType() == 1) {
			//需要判断用户是否点赞过
			Integer userId = null;
			if(null != user){
				userId = user.getId();
			}
			result = shareOrderService.queryShareOrderDetailByType(request.getShareOrderId(),userId);
		} else if (request.getType() == 0) {
			// 老晒家详情必须能够登录
			if (null != user) {
				result = shareOrderService.queryShareOrderDetailById(request);
				// ios 缺陷
				if (result != null) {
					if (CollectionUtils.isEmpty(result.getComments())) {
						result.setComments(new ArrayList<ShareOrderComment>());
					}
				}
			}
		}
		return HttpBaseResponse.success(result);
	}

    @ApiOperation(value = "查询用户楼盘信息")
    @RequestMapping(value = "/queryUserHouseInfo",method = RequestMethod.POST)
    public HttpBaseResponse<UserHouseInfoResponse> queryUserHouseInfo(@RequestBody QueryCollageOrderDetailRequest request) {
    	if(request == null){
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
    	}

		HttpUserInfoRequest user = request.getUserInfo();
		if (null == user) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.USER_NOT_LOGIN);
		}

		UserHouseInfoResponse response = shareOrderService.queryUserHouseInfo(user.getId(),request.getOrderId());
		return HttpBaseResponse.success(response);
    }

	@ApiOperation(value = "晒家表数据整理", notes = "特定人员使用")
	@RequestMapping(value = "/brushShareOrderData", method = RequestMethod.POST)
	public HttpBaseResponse<String> brushShareOrderData(@RequestBody HttpBaseRequest request) {
		String response = shareOrderService.brushShareOrderData();
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "晒家评论表数据整理", notes = "特定人员使用")
	@RequestMapping(value = "/brushShareOrderCommentData", method = RequestMethod.POST)
	public HttpBaseResponse<String> brushShareOrderCommentData(@RequestBody HttpBaseRequest request) {
		String response = shareOrderService.brushShareOrderCommentData();
		return HttpBaseResponse.success(response);
	}
	
}
