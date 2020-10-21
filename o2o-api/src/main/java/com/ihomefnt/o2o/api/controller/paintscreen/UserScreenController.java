package com.ihomefnt.o2o.api.controller.paintscreen;

import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.ArtPushDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.PushArtResultDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.*;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.*;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.manager.constant.paintscreen.ResourceType;
import com.ihomefnt.o2o.intf.manager.constant.paintscreen.UserResourceType;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.paintscreen.UserScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户中心画屏
 *
 * @author liyonggang
 * @create 2018-12-04 14:09
 */
@Api(tags = "【画屏API】")
@RestController
@RequestMapping("/userScreen")
public class UserScreenController {

    @Autowired
    private UserScreenService userScreenService;

    @Autowired
    UserProxy userProxy;

    /**
     * 查询用户已购作品列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询用户已购作品列表", notes = "传入分页参数")
    @RequestMapping(value = "/queryPurchasedList", method = RequestMethod.POST)
    public HttpBaseResponse<PageResponse<ScreenSimpleResponse>> queryPurchasedList(@RequestBody ScreenQueryRequest request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setUserId(user.getId());
        request.setResourceType(UserResourceType.SYSTEM.getCode());
        request.setOperator(user.getId());
        return HttpBaseResponse.success(userScreenService.queryPurchasedList(request));
    }

    /**
     * 查询用户个人作品列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询用户个人作品列表", notes = "传入分页参数")
    @RequestMapping(value = "/queryUserCustomList", method = RequestMethod.POST)
    public HttpBaseResponse<PageResponse<ScreenSimpleResponse>> queryUserCustomList(@RequestBody ScreenQueryRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setUserId(user.getId());
        request.setOperator(user.getId());
        request.setResourceType(UserResourceType.CUSTOM.getCode());
        return HttpBaseResponse.success(userScreenService.queryPurchasedList(request));
    }

    /**
     * 查询用户个人空间使用信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询用户个人空间使用信息", notes = "传入基本参数即可，无业务参数")
    @RequestMapping(value = "/queryUserCustomUsageRate", method = RequestMethod.POST)
    public HttpBaseResponse<UserCustomUsageRateResponse> queryUserCustomUsageRate(@RequestBody HttpBaseRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        return HttpBaseResponse.success(userScreenService.queryUserCustomUsageRate(user.getId()));
    }

    /**
     * 批量删除个人作品
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "批量删除个人作品", notes = "传入个人作品id集合")
    @RequestMapping(value = "/batchDeletePersonalWorks", method = RequestMethod.POST)
    public HttpBaseResponse<UserCustomUsageRateResponse> batchDeletePersonalWorks(@RequestBody OperatingScreenRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setUserId(user.getId());
        request.setOperator(user.getId());
        return userScreenService.batchDeletePersonalWorks(request) ? HttpBaseResponse.success(userScreenService.queryUserCustomUsageRate(user.getId())) : HttpBaseResponse.fail(HttpResponseCode.FAILED, "操作失败！");
    }

    /**
     * 上传个人作品
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "添加个人作品", notes = "传入作品图片id")
    @RequestMapping(value = "/uploadPersonalWorks", method = RequestMethod.POST)
    public HttpBaseResponse<ScreenSimpleResponse> uploadPersonalWorks(@RequestBody OperatingScreenRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setUserId(user.getId());
        request.setOperator(user.getId());
        request.setWidth(request.getImageWidth());
        request.setHeight(request.getImageHeight());
        return HttpBaseResponse.success(userScreenService.uploadPersonalWorks(request));
    }

    /**
     * 推送屏幕
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "推送屏幕", notes = "传入设备id和作品id")
    @RequestMapping(value = "/pushScreen", method = RequestMethod.POST)
    public HttpBaseResponse<RequestIdResponse> pushScreen(@RequestBody ArtPushDto request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setOperator(user.getId());
        request.setUserId(user.getId());

        List<PushArtResultDto> list = userScreenService.pushScreen(request);
        if (CollectionUtils.isEmpty(list)) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, "操作失败！");
        }
        List<Long> requestIdList = new ArrayList<>();
        for(PushArtResultDto pushArtResultDto : list){
            requestIdList.add(pushArtResultDto.getRequestId());
        }
        return HttpBaseResponse.success(new RequestIdResponse(requestIdList, list));
    }

    /**
     * 用户收藏列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户收藏列表", notes = "传入分页参数")
    @RequestMapping(value = "/queryUserCollection", method = RequestMethod.POST)
    public HttpBaseResponse<PageResponse<ScreenSimpleResponse>> queryUserCollection(@RequestBody ScreenQueryRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setUserId(user.getId());
        request.setResourceType(ResourceType.ART_WORK.getCode());
        request.setOperator(user.getId());
        return HttpBaseResponse.success(userScreenService.queryUserCollection(request));
    }

    /**
     * 收藏/取消收藏作品
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "收藏/取消收藏作品", notes = "传入作品id")
    @RequestMapping(value = "/worksCollection", method = RequestMethod.POST)
    public HttpBaseResponse<String> worksCollection(@RequestBody OperatingScreenRequest request) {
        if (request == null || StringUtils.isBlank(request.getAccessToken()) && request.getCollectionAction() == null && request.getProductionId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setUserId(user.getId());
        request.setResourceType(ResourceType.ART_WORK.getCode());
        request.setOperator(user.getId());
        return HttpBaseResponse.success(userScreenService.worksCollection(request));
    }

    /**
     * 绑定/修改画屏
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "绑定/修改画屏", notes = "绑定/修改画屏(绑定新设备时传入macId，产品id)(修改设备信息时传入设备id，设备名称)")
    @RequestMapping(value = "/addOrUpdateScreen", method = RequestMethod.POST)
    public HttpBaseResponse<BindSuccesVo> addOrUpdateScreen(@RequestBody AddOrUpdateScreenRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setUserId(user.getId());
        request.setOperator(user.getId());
        request.setMobile(user.getMobile());
        BindSuccesVo bindSuccesVo = userScreenService.addOrUpdateScreen(request);
        if (bindSuccesVo == null || bindSuccesVo.isResult() == false) {
            HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.FAILED);
        }
        return HttpBaseResponse.success(bindSuccesVo);
    }

    /**
     * 绑定前查询设备信息(扫描动作调用接口)
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "扫描动作调用接口", notes = "绑定前查询设备信息(扫描动作调用接口)")
    @RequestMapping(value = "/queryScreenInfo", method = RequestMethod.POST)
    public HttpBaseResponse<ScreenInfoResponse> queryScreenInfo(@RequestBody AddOrUpdateScreenRequest request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setUserId(user.getId());
        request.setOperator(user.getId());
        HttpBaseResponse<ScreenInfoResponse> screenInfoResponse = userScreenService.queryScreenInfo(request);
        return screenInfoResponse;
    }

    /**
     * 画屏列表查询
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "画屏列表查询", notes = "画屏列表查询")
    @RequestMapping(value = "/queryScreenList", method = RequestMethod.POST)
    public HttpBaseResponse<PageResponse<ScreenInfoResponse>> queryScreenList(@RequestBody ScreenQueryRequest request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        Map<String, Integer> params = new HashMap<>();
        params.put("userId", user.getId());
        params.put("size", request.getPageSize());
        params.put("current", request.getPageNo());
        params.put("operator", user.getId());
        if (request.getQueryType() != null) {
            params.put("queryType", request.getQueryType());
        }
        if (request.getFacilityState() != null) {
            params.put("facilityState", request.getFacilityState());
        }
        return HttpBaseResponse.success(userScreenService.queryScreenList(params));
    }

    /**
     * 审核接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "审核接口", notes = "审核接口")
    @RequestMapping(value = "/updateScreenAuthor", method = RequestMethod.POST)
    public HttpBaseResponse<String> updateScreenAuthor(@RequestBody AuditRequest request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("operator", user.getId());
        params.put("bindId", request.getBindId());
        params.put("bindState", request.getStatus());
        if (userScreenService.updateScreenAuthor(params)) {
            return HttpBaseResponse.success("操作成功");
        }
        return HttpBaseResponse.fail(HttpResponseCode.FAILED, "操作失败");
    }

    /**
     * 移除设备接口(解绑)
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "移除设备接口(解绑)", notes = "移除设备接口(解绑)")
    @RequestMapping(value = "/deleteScreen", method = RequestMethod.POST)
    public HttpBaseResponse<String> deleteScreen(@RequestBody UnbindFacilityDto request) {

        if (request == null || request.getFacilityId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        if (request.getUserId() == null) {
            request.setUserId(user.getId());
        }
        request.setOperator(user.getId());
        if (userScreenService.deleteScreen(request)) {
            return HttpBaseResponse.success("操作成功");
        }
        return HttpBaseResponse.fail(HttpResponseCode.FAILED, "操作失败");
    }

    /**
     * 根据设备ID查询设备详情
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据设备ID查询设备详情", notes = "根据设备ID查询设备详情(传入用户id和设备id)")
    @RequestMapping(value = "/queryUserDevice", method = RequestMethod.POST)
    public HttpBaseResponse<ScreenInfoResponse> queryUserDevice(@RequestBody OperatingScreenRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setOperator(user.getId());
        request.setUserId(user.getId());
        return HttpBaseResponse.success(userScreenService.queryUserDevice(request));
    }


//    /**
//     * 文件上传
//     *
//     * @param multipartFile
//     * @return
//     */
//    @ApiOperation(value = "文件上传", notes = "文件上传")
//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public HttpBaseResponse<UploadFileResponse> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) {
//        UploadFileRequest upload = new UploadFileRequest();
//        upload.setBusinessSystemName("arts-centre");
//        upload.setMultipartFile(multipartFile);
//        upload.setBucketType(1);
//        upload.setSceneName("UserArt");
//        return HttpBaseResponse.success(userScreenService.uploadFile(upload));
//    }


    /**
     * 指令下发接口
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "指令下发接口", notes = "指令下发接口")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public HttpBaseResponse<String> send(@RequestBody SendRequest request) {
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setOperator(user.getId());
        if (userScreenService.send(request)) {
            return HttpBaseResponse.success("操作成功");
        }
        return HttpBaseResponse.fail(HttpResponseCode.FAILED, "操作失败");
    }

    /**
     * 用户个人作品上传+推送屏幕
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户个人作品上传+推送屏幕", notes = "用户个人作品上传+推送屏幕")
    @RequestMapping(value = "/addPersonalWorkPush", method = RequestMethod.POST)
    public HttpBaseResponse<RequestIdResponse> addPersonalWorkPush(@RequestBody AddPersonalWorkPushRequest request) {

        if (request == null || CollectionUtils.isEmpty(request.getMacIdList()) || request.getImageId() == null || StringUtils.isBlank(request.getImageUrl())) {
            return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, "参数错误!");
        }
        HttpUserInfoRequest user = request.getUserInfo();
        if (user == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
        }
        request.setUserId(user.getId());
        return userScreenService.addPersonalWorkPush(request);
    }
}
