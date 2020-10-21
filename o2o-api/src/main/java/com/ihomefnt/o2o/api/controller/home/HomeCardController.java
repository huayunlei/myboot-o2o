package com.ihomefnt.o2o.api.controller.home;

import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.homecard.vo.request.*;
import com.ihomefnt.o2o.intf.domain.homecard.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.log.LogEnum;
import com.ihomefnt.o2o.intf.proxy.user.LogProxy;
import com.ihomefnt.o2o.intf.service.home.HomeCardService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP3.0新版首页视图层
 * 
 * @author ZHAO
 */
@ApiIgnore
@Api(tags = "【产品中心】APP3新版首页api",hidden = true)
@RequestMapping("/homeCard")
@RestController
public class HomeCardController {
	@Autowired
	HomeCardService homeCardService;

	@Autowired
	LogProxy logProxy;

	@Autowired
	UserService userService;

	@ApiOperation(value = "首页推荐版块", notes = "卡片类型（1DNA、2样板套装、3banner、4艺术品、5视频、6特定用户方案、7定制贺卡,8报修）")
	@RequestMapping(value = "/getRecommendBoard", method = RequestMethod.POST)
	public HttpBaseResponse<RecommendBoardListResponseVo> getRecommendBoard(@RequestBody HttpBaseRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		Integer userId = 0;
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto != null) {
			userId = userDto.getId();
			// 增加日志:首页推荐版块
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("deviceToken", request.getDeviceToken());
			params.put("mobile", userDto.getMobile());
			params.put("visitType", LogEnum.LOG_HOMECARD.getCode());
			params.put("action", LogEnum.LOG_HOMECARD.getMsg());
			params.put("userId", userId);
			params.put("appVersion", request.getAppVersion());
			params.put("osType", request.getOsType());
			params.put("pValue", request.getParterValue());
			params.put("cityCode", request.getCityCode());
			logProxy.addLog(params);
		}

		// 推荐版块数据
		List<RecommendBoardResponse> responseList = homeCardService.getRecommendBoard(request, userDto);
		return HttpBaseResponse.success(new RecommendBoardListResponseVo(responseList));
	}

	@ApiOperation(value = "首页产品版块", notes = "")
	@RequestMapping(value = "/getProductBoard", method = RequestMethod.POST)
	public HttpBaseResponse<ProductBoardListResponse> getProductBoard(@RequestBody ProductBoardRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		//todo MOBILE-4941 品质DNA列表iOS加载更多页数据时，显示异常(传入pageNo超过int值范围)，现在先改为iOS请求时，返回全部数据。等版本强更时修改为正常页数
		if(request.getOsType()!=null && request.getOsType()==1 && request.getPageNo()>10000){//ios
			request.setCurPageNo(1);
			request.setPageSize(200);
		}else{//android
			request.setCurPageNo(request.getPageNo().intValue());
		}
		// 产品版块数据
		ProductBoardListResponse responseList = homeCardService.getProductBoard(request);
		return HttpBaseResponse.success(responseList);
	}

	@ApiOperation(value = "首页视频版块", notes = "")
	@RequestMapping(value = "/getVideoBoard", method = RequestMethod.POST)
	public HttpBaseResponse<VideoBoardResponse> getVideoBoard(@RequestBody VideoBoardRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
			// 视频版块数据
		VideoBoardResponse response = homeCardService.getVideoBoard(request);
		return HttpBaseResponse.success(response);
	}

	/**
	 * 首页产品版块筛选条件接口
	 * 
	 * @return
	 */
	@ApiOperation(value = "首页产品版块筛选条件接口", notes = "getProductFilterInfo")
	@RequestMapping(value = "/getProductFilterInfo", method = RequestMethod.POST)
	public HttpBaseResponse<Map> getProductFilterInfo(@RequestBody ProductFilterInfoRequest request) {
		Map response = homeCardService.getProductFilterInfo(request);
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "DNA详情接口", notes = "getDnaDetailById")
	@RequestMapping(value = "/getDnaDetailById", method = RequestMethod.POST)
	public HttpBaseResponse<DnaDetailResponse> getDnaDetailById(@RequestBody DnaDetailRequest request) {
		if (request == null || request.getDnaId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		DnaDetailResponse response = homeCardService.getDnaDetailById(request);
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "DNA硬装清单", notes = "getHardListByCondition")
	@RequestMapping(value = "/getHardListByCondition", method = RequestMethod.POST)
	public HttpBaseResponse<HardSpaceListResponseVo> getHardListByCondition(@RequestBody HardSoftRequest request) {
		if (request == null || request.getDnaId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HardSpaceListResponseVo response = homeCardService.getHardListByCondition(request);
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "DNA软装清单接口", notes = "getSoftListByCondition")
	@RequestMapping(value = "/getSoftListByCondition", method = RequestMethod.POST)
	public HttpBaseResponse<SoftDetailListResponseVo> getSoftListByCondition(@RequestBody HardSoftRequest request) {
		if (request == null || request.getDnaId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		SoftDetailListResponseVo response = homeCardService.getSoftListByCondition(request);
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "DNA点赞接口", notes = "setDnaFavorite")
	@RequestMapping(value = "/setDnaFavorite", method = RequestMethod.POST)
	public HttpBaseResponse<DnaFavoriteResultResponse> setDnaFavorite(@RequestBody DnaDetailRequest request) {
		if (request == null || request.getDnaId() == null
				|| request.getOsType() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}
		DnaFavoriteResultResponse response = homeCardService.setDnaFavorite(userDto.getId(), request.getDnaId());
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "DNA转发操作记录", notes = "转发操作记录")
	@RequestMapping(value = "/setDnaForward", method = RequestMethod.POST)
	public HttpBaseResponse<Void> setDnaForward(@RequestBody DnaDetailRequest request) {
		if (request == null || request.getDnaId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		homeCardService.setDnaForward(request.getDnaId());
		return HttpBaseResponse.success();
	}

	@ApiOperation(value = "根据用户ID和DNA ID查询是否点赞", notes = "queryUserFavoriteFlagByDnaId")
	@RequestMapping(value = "/queryUserFavoriteFlagByDnaId", method = RequestMethod.POST)
	public HttpBaseResponse<UserFavoriteFlagResponseVo> queryUserFavoriteFlagByDnaId(@RequestBody DnaDetailRequest request) {
		if (request == null || request.getDnaId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		HttpUserInfoRequest userDto = request.getUserInfo();
		if (userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}
		UserFavoriteFlagResponseVo response = homeCardService.queryUserFavoriteFlagByDnaId(userDto.getId(), request.getDnaId());
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "新增DNA评论接口", notes = "addDnaComment")
	@RequestMapping(value = "/addDnaComment", method = RequestMethod.POST)
	public HttpBaseResponse<DnaCommentResultResponse> addDnaComment(@RequestBody DnaCommentRequest request) {
		if (request == null || request.getDnaId() == null || StringUtils.isBlank(request.getContent())
			|| StringUtils.isBlank(request.getAccessToken()) || null == request.getOsType()) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		DnaCommentResultResponse response = homeCardService.addDnaComment(request);
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "根据DNA ID查询评论（分页）", notes = "queryDnaCommentListByDnaId")
	@RequestMapping(value = "/queryDnaCommentListByDnaId", method = RequestMethod.POST)
	public HttpBaseResponse<DnaCommentListResponse> queryDnaCommentListByDnaId(@RequestBody DnaCommentListRequest request) {
		if (request == null || request.getDnaId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		DnaCommentListResponse response = homeCardService.queryDnaCommentListByDnaId(request);
		return HttpBaseResponse.success(response);
	}

	/**
	 * DNA详情分享
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "DNA分享接口", notes = "shareDna")
	@RequestMapping(value = "/shareDna", method = RequestMethod.POST)
	public HttpBaseResponse<DnaShareResponse> shareDna(@RequestBody ShareDnaRequest request) {
		if (null == request || request.getShareId() == null || request.getShareId() <= 0) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		DnaShareResponse response = homeCardService.shareDna(request.getShareId().intValue());
		if (null == response) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PRODUCT_NOT_EXISTS);
		}
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "查询设计师的详细信息", notes = "包括设计的DNA列表")
	@RequestMapping(value = "/getDesignerDetailById", method = RequestMethod.POST)
	public HttpBaseResponse<DesignerMoreDetailResponse> getDesignerDetailById(@RequestBody DesignerMoreDetailRequest request) {
		if (null == request || request.getDesignerId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

		DesignerMoreDetailResponse response = homeCardService.getDesignerDetailById(request);
		return HttpBaseResponse.success(response);
	}
	
	
	@ApiOperation(value = "根据户型id户型空间信息", notes = "查询DR户型图及空间用途（户型点选）")
	@RequestMapping(value = "/getHouseInfoByLayoutId", method = RequestMethod.POST)
	public HttpBaseResponse<HouseInfoResponse> getHouseInfoByLayoutId(@RequestBody HouseInfoQueryRequest request) {
		if (homeCardService.getSpaceMarkMustUpdate(request.getAppVersion(),request.getBundleVersions(),request.getOsType())) {
			return HttpBaseResponse.fail(HttpReturnCode.O2O_NEED_FORCE_UPDATE,MessageConstant.MUST_UPDATE_APP);
		}
		HouseInfoResponse response = homeCardService.getHouseInfoByLayoutId(request);
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "根据户型id户型空间信息", notes = "查询DR户型图及空间用途（户型点选）")
	@RequestMapping(value = "/querySubmitDesignRequirement", method = RequestMethod.POST)
	public HttpBaseResponse<SubmitDesignResponse> querySubmitDesignRequirement(@RequestBody HouseInfoQueryRequest request) {
		SubmitDesignResponse response = homeCardService.querySubmitDesignRequirement(request);
		return HttpBaseResponse.success(response);
	}

}
