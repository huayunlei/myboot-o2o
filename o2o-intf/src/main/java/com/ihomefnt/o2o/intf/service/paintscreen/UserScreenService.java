package com.ihomefnt.o2o.intf.service.paintscreen;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.ArtPushDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.PushArtResultDto;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.*;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.*;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;

import java.util.List;
import java.util.Map;

/**
 * @author liyonggang
 * @create 2018-12-04 14:11
 */
public interface UserScreenService {

    /**
     * 查询用户已购作品列表
     *
     * @param request
     * @return
     */
    PageResponse<ScreenSimpleResponse> queryPurchasedList(ScreenQueryRequest request);


    /**
     * 查询用户个人空间使用信息
     *
     * @param userId
     * @return
     */
    UserCustomUsageRateResponse queryUserCustomUsageRate(Integer userId);


    /**
     * 批量删除个人作品
     *
     * @param request
     * @return
     */
    boolean batchDeletePersonalWorks(OperatingScreenRequest request);

    /**
     * 上传个人作品
     *
     * @param request
     * @return
     */
    ScreenSimpleResponse uploadPersonalWorks(OperatingScreenRequest request);

    /**
     * 推送屏幕
     *
     * @param request
     * @return
     */
    List<PushArtResultDto> pushScreen(ArtPushDto request);

    /**
     * 用户收藏列表
     *
     * @param request
     * @return
     */
    PageResponse<ScreenSimpleResponse> queryUserCollection(ScreenQueryRequest request);

    /**
     * 收藏作品
     *
     * @param request
     * @return
     */
    String worksCollection(OperatingScreenRequest request);

    /**
     * 绑定/修改画屏
     *
     * @param request
     * @return
     */
    BindSuccesVo addOrUpdateScreen(AddOrUpdateScreenRequest request);

    /**
     * 画屏列表查询
     *
     * @param params
     * @return
     */
    PageResponse<ScreenInfoResponse> queryScreenList(Map<String, Integer> params);

    /**
     * 审核接口
     *
     * @param params
     * @return
     */
    boolean updateScreenAuthor(Map<String, Object> params);

    /**
     * 移除设备接口(解绑)
     *
     * @param request
     * @return
     */
    boolean deleteScreen(UnbindFacilityDto request);


    /**
     * 绑定前查询设备信息(扫描动作调用接口)
     *
     * @param request
     * @return
     */
    HttpBaseResponse<ScreenInfoResponse> queryScreenInfo(AddOrUpdateScreenRequest request);

    /**
     * 根据设备ID查询设备详情
     *
     * @param request
     * @return
     */
    ScreenInfoResponse queryUserDevice(OperatingScreenRequest request);

    /**
     * 指令下发
     * @param request
     * @return
     */
    boolean send(SendRequest request);

    /**
     * 用户个人作品上传+推送屏幕
     * @param request
     * @return
     */
    HttpBaseResponse<RequestIdResponse> addPersonalWorkPush(AddPersonalWorkPushRequest request);
}
