package com.ihomefnt.o2o.service.service.paintscreen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.ArtPushDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.ImageInfoDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.PushArtResultDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.ResourceDetailDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.ResourceListDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.UserArtDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.AddOrUpdateScreenRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.AddPersonalWorkPushRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.OperatingScreenRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.ScreenQueryRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.SendRequest;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.UnbindFacilityDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.BasePageVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.BindSuccesVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ImageDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.RequestIdResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenInfoResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.ScreenSimpleResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.SimpleMember;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.UserArtVo;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.UserCustomUsageRateResponse;
import com.ihomefnt.o2o.intf.manager.constant.paintscreen.CollectionAction;
import com.ihomefnt.o2o.intf.manager.constant.paintscreen.UserResourceType;
import com.ihomefnt.o2o.intf.proxy.paintscreen.UserScreenProxy;
import com.ihomefnt.o2o.intf.service.paintscreen.UserScreenService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户中心画屏
 *
 * @author liyonggang
 * @create 2018-12-04 14:11
 */
@Service
public class UserScreenServiceImpl implements UserScreenService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserScreenServiceImpl.class);
    @Autowired
    private UserScreenProxy userScreenProxy;

    /**
     * 查询用户已购作品列表
     *
     * @param request
     * @return
     */
    @Override
    public PageResponse<ScreenSimpleResponse> queryPurchasedList(ScreenQueryRequest request) {
        BasePageVo<UserArtDto> basePageVo = userScreenProxy.queryPurchasedList(request);
        if (basePageVo == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(basePageVo.getRows())) {
            return new PageResponse<>(Collections.emptyList(), basePageVo.getCurrent(), request.getPageSize(), basePageVo.getTotalCount(), basePageVo.getPages());
        }
        List<Integer> imageIds = Lists.newArrayList();
        List<ScreenSimpleResponse> list = Lists.newArrayList();
        if (request.getResourceType().equals(UserResourceType.CUSTOM.getCode())) {
            //用户上传画作
            basePageVo.getRows().forEach(userArtDto -> {
                ScreenSimpleResponse screenSimpleResponse = new ScreenSimpleResponse();
                screenSimpleResponse.setArtId(userArtDto.getUserArtId());
                screenSimpleResponse.setArtImage(userArtDto.getArtImage());
                screenSimpleResponse.setWidth(userArtDto.getWidth());
                screenSimpleResponse.setHeight(userArtDto.getHeight());
                if (userArtDto.getArtImage() != null) {
                    imageIds.add(Integer.parseInt(userArtDto.getArtImage()));
                }
                list.add(screenSimpleResponse);
            });
        } else {
            //应用内画作
            basePageVo.getRows().forEach(userArtDto -> {
                ScreenSimpleResponse screenSimpleResponse = new ScreenSimpleResponse();
                screenSimpleResponse.setArtDeadline(userArtDto.getArtDeadline());
                screenSimpleResponse.setArtImage(userArtDto.getArtImage());
                screenSimpleResponse.setArtId(userArtDto.getArtId());
                screenSimpleResponse.setWidth(userArtDto.getWidth());
                screenSimpleResponse.setHeight(userArtDto.getHeight());
                screenSimpleResponse.setArtName(userArtDto.getArtName());
                String artExpirationTime = userArtDto.getArtExpirationTime();
                if (userArtDto.getArtImage() != null) {
                    imageIds.add(Integer.parseInt(userArtDto.getArtImage()));
                }
                Date artExpirationDateTime = null;
                if (StringUtils.isNotBlank(artExpirationTime)) {
                    try {
                        artExpirationDateTime = setSFM(artExpirationTime, "yyyy-MM-dd HH:mm:ss", 23, 59, 59);
                    } catch (Exception e) {
                        LOGGER.debug("date parse error param {},error{}", artExpirationTime, e);
                    }
                }
                if (artExpirationDateTime != null) {
                        screenSimpleResponse.setOverdue(artExpirationDateTime.getTime() < System.currentTimeMillis());
                        screenSimpleResponse.setArtExpirationTime(DateFormatUtils.format(artExpirationDateTime, "yyyy/MM/dd"));
                }
                list.add(screenSimpleResponse);
            });
        }
        if (CollectionUtils.isNotEmpty(imageIds)) {
            try {
                Map<Integer, String> urls = userScreenProxy.getUrls(imageIds, request.getWidth(),2);
                if (MapUtils.isNotEmpty(urls)) {
                    list.forEach(screenSimpleResponse -> screenSimpleResponse.setArtImage(urls.get(Integer.parseInt(screenSimpleResponse.getArtImage())))
                    );
                }
            } catch (Exception e) {
                LOGGER.error("image change o2o-exception , more info :", e);
            }
        }
        return new PageResponse<>(list, basePageVo.getCurrent(), request.getPageSize(), basePageVo.getTotalCount(), basePageVo.getPages());
    }


    /**
     * 查询用户个人空间使用信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserCustomUsageRateResponse queryUserCustomUsageRate(Integer userId) {
        return userScreenProxy.queryUserCustomUsageRate(userId);
    }

    /**
     * 批量删除个人作品
     *
     * @param request
     * @return
     */
    @Override
    public boolean batchDeletePersonalWorks(OperatingScreenRequest request) {
        return userScreenProxy.batchDeletePersonalWorks(request);
    }

    private static Date setSFM(String dateTime, String patterns, int hour, int minute, int second) throws ParseException {
        Date artExpirationDateTime = DateUtils.parseDate(dateTime, patterns);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(artExpirationDateTime);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, minute, second);
        return calendar.getTime();
    }

    /**
     * 上传个人作品
     *
     * @param request
     * @return
     */
    @Override
    public ScreenSimpleResponse uploadPersonalWorks(OperatingScreenRequest request) {
        UserCustomUsageRateResponse userCustomUsageRateResponse = userScreenProxy.queryUserCustomUsageRate(request.getUserId());
        if (userCustomUsageRateResponse == null) {
            throw new BusinessException(HttpResponseCode.FAILED, "用户个人空间异常！");
        }
        if (userCustomUsageRateResponse.getSum() <= userCustomUsageRateResponse.getUsed()) {
            throw new BusinessException(HttpResponseCode.FAILED, "用户个人空间不足！");
        }
        UserArtDto userArtDto = userScreenProxy.uploadPersonalWorks(request);
        if (userArtDto == null) {
            return null;
        }
        ScreenSimpleResponse screenSimpleResponse = new ScreenSimpleResponse();
        screenSimpleResponse.setArtId(userArtDto.getUserArtId());
        screenSimpleResponse.setArtImage(userArtDto.getArtImage());
        screenSimpleResponse.setHeight(userArtDto.getHeight());
        screenSimpleResponse.setWidth(userArtDto.getWidth());
        return screenSimpleResponse;
    }

    /**
     * 推送屏幕
     *
     * @param request
     * @return
     */
    @Override
    public List<PushArtResultDto> pushScreen(ArtPushDto request) {
        return userScreenProxy.pushScreen(request);
    }

    /**
     * 用户收藏列表
     *
     * @param request
     * @return
     */
    @Override
    public PageResponse<ScreenSimpleResponse> queryUserCollection(ScreenQueryRequest request) {

        BasePageVo<ResourceListDto> result = userScreenProxy.queryUserCollection(request);
        if (result == null) {
            return null;
        }
        List<ScreenSimpleResponse> list = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(result.getRows())) {
            result.getRows().forEach(item -> {
                ScreenSimpleResponse screenSimpleResponse = new ScreenSimpleResponse();
                ResourceDetailDto resourceDetail = item.getResourceDetail();
                if (resourceDetail != null) {
                    BeanUtils.copyProperties(resourceDetail, screenSimpleResponse);
                    screenSimpleResponse.setBrowseCount(item.getBrowseCount());
                    list.add(screenSimpleResponse);
                }
            });
        }
        if (!list.isEmpty()) {
            try {
                List<Integer> collect = list.stream().map(screenSimpleResponse -> Integer.parseInt(screenSimpleResponse.getArtImage())).collect(Collectors.toList());
                Map<Integer, String> urls = userScreenProxy.getUrls(collect, request.getWidth(),2);
                if (MapUtils.isNotEmpty(urls)) {
                    list.parallelStream().forEach(screenSimpleResponse -> screenSimpleResponse.setArtImage(urls.get(Integer.parseInt(screenSimpleResponse.getArtImage()))));
                }
            } catch (Exception e) {
                LOGGER.error("image change o2o-exception , more info :", e);
            }
        }
        return new PageResponse<>(list, result.getCurrent(), request.getPageSize(), result.getTotalCount(), result.getPages());
    }

    /**
     * 收藏作品
     *
     * @param request
     * @return
     */
    @Override
    public String worksCollection(OperatingScreenRequest request) {
        boolean result = false;
        if (request.getCollectionAction().equals(CollectionAction.COLLECT.getCode())) {
            result = userScreenProxy.collect(request);
        } else if (request.getCollectionAction().equals(CollectionAction.CANCEL_COLLECT.getCode())) {
            result = userScreenProxy.collectCancel(request);
        }
        return result ? MessageConstant.SUCCESS : MessageConstant.FAILED;
    }

    /**
     * 绑定/修改画屏
     *
     * @param request
     * @return
     */
    @Override
    public BindSuccesVo addOrUpdateScreen(AddOrUpdateScreenRequest request) {
        BindSuccesVo bindSuccesVo = new BindSuccesVo();
        boolean result =false;
        if (StringUtils.isNotBlank(request.getMacId())) {
            bindSuccesVo = userScreenProxy.bind(request);
        } else {
            if (request.getBindType() == 1) {
                result = userScreenProxy.upUpdateScreenInfo(request);
            } else {
                result = userScreenProxy.updateNickName(request);
            }
            bindSuccesVo.setResult(result);
        }
        return bindSuccesVo;
    }

    /**
     * 画屏列表查询
     *
     * @param params
     * @return
     */
    @Override
    public PageResponse<ScreenInfoResponse> queryScreenList(Map<String, Integer> params) {
        BasePageVo<ScreenInfoResponse> screenInfoResponseBasePageVo = userScreenProxy.queryScreenList(params);
        PageResponse<ScreenInfoResponse> result = null;
        if (screenInfoResponseBasePageVo != null) {
            Integer userId = params.get("userId");
            List<ScreenInfoResponse> rows = screenInfoResponseBasePageVo.getRows();
            if (CollectionUtils.isNotEmpty(rows)) {
                Integer queryType = params.get("queryType");
                if ((queryType != null && queryType == 1)) {
                    rows.removeIf(screenInfoResponse -> screenInfoResponse.getBindState() == 2);
                }
                rows.forEach(screenInfoResponse -> {
                    List<SimpleMember> bindUserVoList = screenInfoResponse.getBindUserVoList();
                    if (CollectionUtils.isNotEmpty(bindUserVoList)) {
                        bindUserVoList.removeIf(simpleMember -> simpleMember.getUserId().equals(userId));
                    }
                });
                rows.sort((o1, o2) -> o2.getFacilityState().compareTo(o1.getFacilityState()));
            }
            result = new PageResponse<>();
            result.setList(rows);
            result.setPageNo(screenInfoResponseBasePageVo.getCurrent());
            result.setTotalCount(screenInfoResponseBasePageVo.getTotalCount());
            result.setTotalPage(screenInfoResponseBasePageVo.getPages());
        }
        return result;
    }

    /**
     * 审核接口
     *
     * @param params
     * @return
     */
    @Override
    public boolean updateScreenAuthor(Map<String, Object> params) {

        return userScreenProxy.updateScreenAuthor(params);
    }

    /**
     * 移除设备接口(解绑)
     *
     * @param request
     * @return
     */
    @Override
    public boolean deleteScreen(UnbindFacilityDto request) {
        OperatingScreenRequest query = new OperatingScreenRequest();
        query.setUserId(request.getUserId());
        query.setOperator(request.getOperator());
        query.setDeviceId(request.getFacilityId());
        query.setWidth(request.getWidth());
        ScreenInfoResponse screenInfoResponse = queryUserDevice(query);
        boolean flag = false;
        if (screenInfoResponse != null && screenInfoResponse.getBindType() == 1) {//当前用户是否是管理员, 0:否,1:是
            List<SimpleMember> bindUserVoList = screenInfoResponse.getBindUserVoList();
            if (CollectionUtils.isNotEmpty(bindUserVoList)) {
                for (SimpleMember simpleMember : bindUserVoList) {
                    if (1 == simpleMember.getBindState() && !request.getUserId().equals(simpleMember.getUserId())) {//绑定状态 1已绑定,2待审核
                        flag = true;
                        break;
                    }
                }
            }
        }
        if (flag) {
            throw new BusinessException(HttpResponseCode.FAILED, "该设备有其他绑定用户,管理员无法解绑");
        }
        return userScreenProxy.unbind(request);
    }

    /**
     * 绑定前查询设备信息(扫描动作调用接口)
     *
     * @param request
     * @return
     */
    @Override
    public HttpBaseResponse<ScreenInfoResponse> queryScreenInfo(AddOrUpdateScreenRequest request) {
        return userScreenProxy.queryScreenInfo(request);
    }

    /**
     * 根据设备ID查询设备详情
     *
     * @param request
     * @return
     */
    @Override
    public ScreenInfoResponse queryUserDevice(OperatingScreenRequest request) {
        ScreenInfoResponse screenInfoResponse = userScreenProxy.queryUserDevice(request);
        if (screenInfoResponse != null) {
            if (screenInfoResponse.getFacilityProperty() != null) {
                JSONObject facilityProperty = screenInfoResponse.getFacilityProperty();
                List<ImageDto> imageList = JsonUtils.json2list((String) facilityProperty.get("imageList"), ImageDto.class);
                if (CollectionUtils.isNotEmpty(imageList)) {
                    imageList.removeIf(imageDto ->
                    {
                        ImageDto image = JSON.parseObject(JSON.toJSONString(imageDto), ImageDto.class);
                        return image.getImgType() == 1;
                    });//1 是广告图片

                    List<Integer> collect = imageList.parallelStream().map(imageDto -> {
                        ImageDto image = JSON.parseObject(JSON.toJSONString(imageDto), ImageDto.class);
                        return Integer.parseInt(image.getImgId());
                    }).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(collect)) {
                        Map<Integer, String> urls = userScreenProxy.getUrls(collect, request.getWidth(),2);
                        //根据图片ID查宽高
                        List<UserArtVo> imageWidthList = userScreenProxy.getImageWidth(collect, request.getUserId());
                        if (MapUtils.isNotEmpty(urls))
                            imageList.parallelStream().forEach(image ->
                            {
                                image.setImgUrl(urls.get(Integer.parseInt(image.getImgId())));
                                if (CollectionUtils.isNotEmpty(imageWidthList)) {
                                    imageWidthList.parallelStream().forEach(imageDtoSe -> {
                                        if (imageDtoSe.getArtImage().equals(image.getImgId())) {
                                            image.setHeight(imageDtoSe.getHeight());
                                            image.setWidth(imageDtoSe.getWidth());
                                        }
                                    });
                                }

                            });
                    }
                }
                facilityProperty.put("imageList",imageList);
            }
            if (CollectionUtils.isNotEmpty(screenInfoResponse.getBindUserVoList())) {
                screenInfoResponse.getBindUserVoList().removeIf(simpleMember -> simpleMember.getUserId().equals(request.getOperator()));
            }
        }
        return screenInfoResponse;
    }


    /**
     * 指令下发接口
     *
     * @param request
     * @return
     */
    @Override
    public boolean send(SendRequest request) {
        return userScreenProxy.send(request);
    }

    /**
     * 添加个人画作+推送屏幕
     * @param request
     * @return
     */
    @Override
    public HttpBaseResponse<RequestIdResponse> addPersonalWorkPush(AddPersonalWorkPushRequest request) {
        ImageInfoDto imageInfoDto = userScreenProxy.queryFileInfoById(request.getImageId());
        if (imageInfoDto==null){
            throw new BusinessException(HttpResponseCode.FAILED,"图片获取失败！");
        }
        if (imageInfoDto.getWidth()==null||imageInfoDto.getHeight()==null){
            throw new BusinessException(HttpResponseCode.FAILED,"图片信息异常，请尝试重新上传！");
        }
        Integer userId = request.getUserId();
        OperatingScreenRequest operatingScreenRequest = new OperatingScreenRequest();
        operatingScreenRequest.setImageId(request.getImageId());
        operatingScreenRequest.setHeight(imageInfoDto.getHeight());
        operatingScreenRequest.setWidth(imageInfoDto.getWidth());
        operatingScreenRequest.setUserId(userId);
        operatingScreenRequest.setOperator(userId);
        ScreenSimpleResponse screenSimpleResponse = uploadPersonalWorks(operatingScreenRequest);
        if (screenSimpleResponse == null || screenSimpleResponse.getArtId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, "添加画作失败！");
        }
        ArtPushDto artPushDto = new ArtPushDto();
        artPushDto.setUserId(userId);
        artPushDto.setOperator(userId);
        artPushDto.setUserArtId(screenSimpleResponse.getArtId());
        artPushDto.setMacIdList(request.getMacIdList());
        artPushDto.setPushType(1);
        artPushDto.setResourceType(UserResourceType.CUSTOM.getCode());
        List<PushArtResultDto> pushArtResultDtoList = pushScreen(artPushDto);
        if(CollectionUtils.isEmpty(pushArtResultDtoList)){
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, "图片推送失败！");
        }
        List<Long> requestIdList = new ArrayList<>();
        for(PushArtResultDto pushArtResultDto : pushArtResultDtoList){
            requestIdList.add(pushArtResultDto.getRequestId());
        }
        return HttpBaseResponse.success(new RequestIdResponse(requestIdList, pushArtResultDtoList));

    }

}
