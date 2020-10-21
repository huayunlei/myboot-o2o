package com.ihomefnt.o2o.intf.proxy.paintscreen;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.*;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.*;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.*;

import java.util.List;
import java.util.Map;

/**
 * @author liyonggang
 * @create 2018-12-04 14:32
 */
public interface UserScreenProxy {
    /**
     * 画屏列表查询
     *
     * @param params
     * @return
     */
    BasePageVo<ScreenInfoResponse> queryScreenList(Map<String, Integer> params);

    /**
     * 审核接口
     *
     * @param params
     * @return
     */
    boolean updateScreenAuthor(Map<String, Object> params);

    /**
     * 解绑设备
     *
     * @param request
     * @return
     */
    boolean unbind(UnbindFacilityDto request);

    /**
     * 绑定设备
     *
     * @param request
     * @return
     */
    BindSuccesVo bind(AddOrUpdateScreenRequest request);

    /**
     * 修改设备信息
     *
     * @param request
     * @return
     */
    boolean upUpdateScreenInfo(AddOrUpdateScreenRequest request);

    /**
     * 绑定前查询设备信息(扫描动作调用接口)
     *
     * @param request
     * @return
     */
    HttpBaseResponse<ScreenInfoResponse> queryScreenInfo(AddOrUpdateScreenRequest request);

    /**
     * 收藏画作/画集
     *
     * @param request
     * @return
     */
    boolean collect(OperatingScreenRequest request);

    /**
     * 取消收藏画作画集
     *
     * @param request
     * @return
     */
    boolean collectCancel(OperatingScreenRequest request);

    /**
     * 画作/画集收藏列表
     *
     * @param request
     * @return
     */
    BasePageVo<ResourceListDto> queryUserCollection(ScreenQueryRequest request);

    /**
     * 推送画作
     *
     * @param request
     * @return
     */
    List<PushArtResultDto> pushScreen(ArtPushDto request);

    /**
     * 用户画作列表
     *
     * @param request
     * @return
     */
    BasePageVo<UserArtDto> queryPurchasedList(ScreenQueryRequest request);

    /**
     * 查询用户个人空间使用
     *
     * @param userId
     * @return
     */
    UserCustomUsageRateResponse queryUserCustomUsageRate(Integer userId);

    /**
     * 添加个人画作
     *
     * @param request
     * @return
     */
    UserArtDto uploadPersonalWorks(OperatingScreenRequest request);

    /**
     * 修改绑定昵称
     *
     * @param request
     * @return
     */
    boolean updateNickName(AddOrUpdateScreenRequest request);

    /**
     * 批量移除个人作品
     *
     * @param request
     * @return
     */
    boolean batchDeletePersonalWorks(OperatingScreenRequest request);

    /**
     * 根据设备ID查询设备详情
     *
     * @param request
     * @return
     */
    ScreenInfoResponse queryUserDevice(OperatingScreenRequest request);

    /**
     * 批量获取图片url
     * @param fileIds
     * @param width
     * @param type 1为正常切图逻辑 2为图片降档
     * @return
     */
    Map<Integer, String> getUrls(List<Integer> fileIds,Integer width,Integer type);


    /**
     * 图片宽高获取
     *
     * @param collect
     * @param userId
     * @return
     */
    List<UserArtVo> getImageWidth(List<Integer> collect, Integer userId);

    /**
     * 指令下发
     *
     * @param request
     * @return
     */
    boolean send(SendRequest request);

    /**
     * 通过文件url获取文件信息
     *
     * @param url
     * @return
     */
    ImageInfoDto queryImageInfoByUrl(String url);

    /**
     * 通过文件id
     *
     * @param imageId
     * @return
     */
    ImageInfoDto queryFileInfoById(String imageId);
}
