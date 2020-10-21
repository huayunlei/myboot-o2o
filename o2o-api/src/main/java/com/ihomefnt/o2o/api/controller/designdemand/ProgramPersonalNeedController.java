package com.ihomefnt.o2o.api.controller.designdemand;

import com.ihomefnt.o2o.api.controller.BaseController;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.personalneed.dto.CommitDesignDemandVo;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.request.*;
import com.ihomefnt.o2o.intf.domain.personalneed.vo.response.*;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.service.designDemand.ProgramPersonalNeedService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户个性化需求
 * Author: ZHAO
 * Date: 2018年5月25日
 */
@RestController
@Api(tags = "【设计任务相关】")
@RequestMapping("/personalneed")
public class ProgramPersonalNeedController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private ProgramPersonalNeedService programPersonalNeedService;

	@ApiOperation(value = "查询用户是否可以提需求", notes = "查询用户是否可以提需求")
	@PostMapping(value = "/checkUserDemond")
	public HttpBaseResponse<Boolean> checkUserDemond(@RequestBody ProgramOrderDetailRequest request) {
		if (request == null || StringUtils.isBlank(request.getAccessToken()) || request.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		UserDto userDto = userService.getUserByToken(request.getAccessToken());
		if (userDto == null || userDto.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}

		boolean checkResult = programPersonalNeedService.checkUserDemond(request.getOrderId(),userDto.getId());
		return HttpBaseResponse.success(checkResult);
	}

	@ApiOperation(value = "提交方案设计需求", notes = "提交方案设计需求")
	@PostMapping(value = "/commitDesignDemand")
	public HttpBaseResponse<CommitDesignDemandVo> commitDesignDemand(@RequestBody CommitDesignRequest request) {
		if (request == null || StringUtils.isBlank(request.getAccessToken()) || CollectionUtils.isEmpty(request.getDnaRoomList())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HttpUserInfoRequest userInfo = request.getUserInfo();
		if (userInfo == null || userInfo.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}

		CommitDesignDemandVo commitResult = programPersonalNeedService.commitDesignDemand(request, userInfo.getId());
		if(commitResult == null || commitResult.getErrorCode() != 1) {
			String msg = null;
			Integer errorCode = (commitResult == null || null == commitResult.getErrorCode()) ? -1 : commitResult.getErrorCode();
			switch (errorCode) {
				case -1001:
					msg = "您提交的设计需求已在设计中了";
					break;
				case -1002:
					msg = "您已提交了设计需求哦";
					break;
				default:
					msg = "提交失败";
			}
			return HttpBaseResponse.fail(errorCode.longValue(), msg);
		}
		return HttpBaseResponse.success(commitResult);
	}

	@ApiOperation(value = "查询设计需求", notes = "查询设计需求")
	@PostMapping(value = "/queryDesignDemond")
	public HttpBaseResponse<PersonalDesignResponse> queryDesignDemond(@RequestBody ProgramOrderDetailRequest request) {
		if (request == null || request.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		Integer width = 0;
		if(request.getWidth() != null){
			width = request.getWidth();
		}
		PersonalDesignResponse designResponse = programPersonalNeedService.queryDesignDemond(request.getOrderId(), width, request.getMobileNum(),request.getOsType());
		return HttpBaseResponse.success(designResponse);
	}

	@ApiOperation(value = "查询DNA集合信息", notes = "查询DNA集合信息")
	@PostMapping(value = "/randomQueryDna")
	public HttpBaseResponse<PersonalArtisticListResponseVo> randomQueryDna(@RequestBody HttpBaseRequest request) {
		Integer width = 0;
		if(request != null && request.getWidth() != null){
			width = request.getWidth();
		}
		List<PersonalArtisticResponse> artisticResponses = programPersonalNeedService.randomQueryDna(width);
		PersonalArtisticListResponseVo result = new PersonalArtisticListResponseVo();
		result.setDnaList(artisticResponses);
		return HttpBaseResponse.success(result);
	}
	
	@ApiOperation(value = "查询家庭个性标签集合", notes = "查询家庭个性标签集合")
	@PostMapping(value = "/queryPersonalTagList")
	public HttpBaseResponse<PersonalTagListResponseVo> queryPersonalTagList() {
		List<String> tagList = programPersonalNeedService.queryPersonalTagList();
		PersonalTagListResponseVo result = new PersonalTagListResponseVo();
		result.setTagList(tagList);
		return HttpBaseResponse.success(result);
	}

	@ApiOperation(value = "查询设计需求记录列表", notes = "选风格历史记录")
	@PostMapping(value = "/queryStyleRecord")
	public HttpBaseResponse<StyleRecordResponse> queryStyleRecord(@RequestBody StyleRecordRequest request) {
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.PARAMS_NOT_EXISTS);
		}
		HttpUserInfoRequest userInfoRequest = request.getUserInfo();
		if (userInfoRequest == null || userInfoRequest.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.ADMIN_ILLEGAL);
		}

		StyleRecordResponse recordResponse = programPersonalNeedService.queryStyleRecord(request,userInfoRequest.getId());
		return HttpBaseResponse.success(recordResponse);
	}
	
	@ApiOperation(value = "筛选dna空间", notes = "筛选dna空间")
    @PostMapping(value = "/filterDnaRoomByPurpose")
    public HttpBaseResponse<List<FilterQueryDnaRoomResponse>> filterDnaRoomByPurpose (@RequestBody FilterDnaRoomReq request) {
		if (request == null || null == request.getDnaId()
				|| CollectionUtils.isEmpty(request.getMarkAndPurposeList())) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}

		List<FilterQueryDnaRoomResponse> response = programPersonalNeedService.filterDnaRoomByPurpose(request);
		return HttpBaseResponse.success(response);
	}
	
	@ApiOperation(value = "根据用途id分页查询dna空间信息", notes = "根据用途id分页查询dna空间信息")
    @PostMapping(value = "/queryDnaRoomByPurposeId")
    public HttpBaseResponse<DnaRoomListResponse> queryDnaRoomByPurposeId (@RequestBody QueryDnaRoomRequest request) {
		if(request == null || null == request.getDnaPurposeId()){
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.PARAMS_NOT_EXISTS);
		}
		DnaRoomListResponse response = programPersonalNeedService.queryDnaRoomByPurposeId(request);
		return HttpBaseResponse.success(response);
	}

	@ApiOperation(value = "根据orderId查询设计需求状态", notes = "根据orderId查询设计需求状态")
	@PostMapping(value = "/queryDesignStatus")
	public HttpBaseResponse<QueryDesignStatusResponse> queryDesignStatus(@RequestBody QueryDesignStatusRequest request) {
		if (request == null || request.getOrderId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HttpUserInfoRequest userInfo = request.getUserInfo();
		if (userInfo == null || userInfo.getId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.TOKEN_EXPIRE, MessageConstant.USER_NOT_LOGIN);
		}

		return HttpBaseResponse.success(programPersonalNeedService.queryDesignStatus(request));
	}
	
}
