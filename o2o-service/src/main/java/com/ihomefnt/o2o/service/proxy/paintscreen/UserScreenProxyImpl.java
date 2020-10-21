package com.ihomefnt.o2o.service.proxy.paintscreen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.beust.jcommander.internal.Maps;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.paintscreen.dto.*;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.request.*;
import com.ihomefnt.o2o.intf.domain.paintscreen.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.proxy.ArtsCentreServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.SmartServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.UnifyfileServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.paintscreen.UserScreenProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户中心画屏
 *
 * @author liyonggang
 * @create 2018-12-04 14:32
 */
@Repository
public class UserScreenProxyImpl implements UserScreenProxy {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserScreenProxyImpl.class);

    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    /**
     * 画屏列表查询
     *
     * @param params
     * @return
     */
    @Override
    public BasePageVo<ScreenInfoResponse> queryScreenList(Map<String, Integer> params) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(SmartServiceNameConstants.QUERY_USER_FACILITY_PAGE, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }

        return JSONObject.parseObject(JsonUtils.obj2json(responseVo.getData()), new TypeReference<BasePageVo<ScreenInfoResponse>>() {
        });
    }

    /**
     * 审核接口
     *
     * @param params
     * @return
     */
    @Override
    public boolean updateScreenAuthor(Map<String, Object> params) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(SmartServiceNameConstants.FACILITY_BIND_AUDIT, params, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }

    /**
     * 解绑设备
     *
     * @param request
     * @return
     */
    @Override
    public boolean unbind(UnbindFacilityDto request) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(SmartServiceNameConstants.FACILITY_BIND_UNBIND, request, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }

    /**
     * 绑定设备
     *
     * @param request
     * @return
     */
    @Override
    public BindSuccesVo bind(AddOrUpdateScreenRequest request) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(SmartServiceNameConstants.FACILITY_BIND_BIND, request, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            if (responseVo != null && responseVo.getCode() == 200001) {
                throw new BusinessException("画屏未联网，请检查网络！");
            }
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(responseVo.getData()), BindSuccesVo.class);
    }

    /**
     * 修改设备信息
     *
     * @param request
     * @return
     */
    @Override
    public boolean upUpdateScreenInfo(AddOrUpdateScreenRequest request) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(SmartServiceNameConstants.FACILITY_UPDATE, request, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }

    @Override
    public HttpBaseResponse<ScreenInfoResponse> queryScreenInfo(AddOrUpdateScreenRequest request) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(SmartServiceNameConstants.FACILITY_BIND_FIND, request, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.FAILED);
        }
        if (!responseVo.isSuccess()) {
            String msg = getMessageByCode(responseVo.getCode());
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, msg);
        }
        return HttpBaseResponse.success(JSONObject.parseObject(JsonUtils.obj2json(responseVo.getData()), new TypeReference<ScreenInfoResponse>() {
        }));
    }

    /**
     * 收藏画作/画集
     *
     * @param request
     * @return
     */
    @Override
    public boolean collect(OperatingScreenRequest request) {
        Map<? extends Serializable, ? extends Serializable> param = Maps.newHashMap("resourceType", request.getResourceType(), "operator", request.getOperator(), "userId", request.getUserId(), "resourceId", request.getProductionId());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(ArtsCentreServiceNameConstants.USER_COLLECT_COLLECT, param, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }
        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }

    /**
     * 取消收藏画作画集
     *
     * @param request
     * @return
     */
    @Override
    public boolean collectCancel(OperatingScreenRequest request) {
        Map<? extends Serializable, ? extends Serializable> param = Maps.newHashMap("resourceType", request.getResourceType(), "operator", request.getOperator(), "userId", request.getUserId(), "resourceId", request.getProductionId());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(ArtsCentreServiceNameConstants.USER_COLLECT_CANCEL, param, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }
        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }

    /**
     * 画作/画集收藏列表
     *
     * @param request
     * @return
     */
    @Override
    public BasePageVo<ResourceListDto> queryUserCollection(ScreenQueryRequest request) {
        Map<? extends Serializable, ? extends Serializable> param = Maps.newHashMap("current", request.getPageNo(), "operator", request.getOperator(), "resourceType", request.getResourceType(), "size", request.getPageSize(), "userId", request.getUserId());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.USER_COLLECT_PAGE, param, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }

        return JSON.parseObject(JSON.toJSONString(responseVo.getData()), new TypeReference<BasePageVo<ResourceListDto>>() {
        });
    }

    /**
     * 推送画作
     *
     * @param request
     * @return
     */
    @Override
    public List<PushArtResultDto> pushScreen(ArtPushDto request) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(ArtsCentreServiceNameConstants.PUSH_ART_PUSH, request, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null || !responseVo.isSuccess()) {
            return null;
        }
        return JsonUtils.json2list(JSON.toJSONString(responseVo.getData()), PushArtResultDto.class);
    }

    /**
     * 用户画作列表
     *
     * @param request
     * @return
     */
    @Override
    public BasePageVo<UserArtDto> queryPurchasedList(ScreenQueryRequest request) {
        Map<? extends Serializable, ? extends Serializable> params = Maps.newHashMap("current", request.getPageNo(), "resourceType", request.getResourceType(), "size", request.getPageSize(), "userId", request.getUserId(), "operator", request.getOperator());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.USER_ART_PAGE, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(responseVo.getData()), new TypeReference<BasePageVo<UserArtDto>>() {
        });
    }

    @Override
    public UserCustomUsageRateResponse queryUserCustomUsageRate(Integer userId) {
        Map<? extends Serializable, ? extends Serializable> params = Maps.newHashMap("userId", userId, "operator", userId);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(ArtsCentreServiceNameConstants.USER_ART_QUERY_SPACE, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(JsonUtils.obj2json(responseVo.getData()));
        return new UserCustomUsageRateResponse(jsonObject.getInteger("totalSpace"), jsonObject.getInteger("useSpace"));
    }

    /**
     * 添加个人画作
     *
     * @param request
     * @return
     */
    @Override
    public UserArtDto uploadPersonalWorks(OperatingScreenRequest request) {
        Map<? extends Serializable, ? extends Serializable> params = Maps.newHashMap("artImage", request.getImageId(), "userId", request.getUserId(), "operator", request.getOperator(), "height", request.getHeight(), "width", request.getWidth());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(ArtsCentreServiceNameConstants.USER_ART_ADD, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(responseVo.getData()), UserArtDto.class);
    }

    /**
     * 修改绑定昵称
     *
     * @param request
     * @return
     */
    @Override
    public boolean updateNickName(AddOrUpdateScreenRequest request) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(SmartServiceNameConstants.FACILITY_BIND_UPDATE_NICK_NAME, request, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }
        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }

    /**
     * 批量移除个人作品
     *
     * @param request
     * @return
     */
    @Override
    public boolean batchDeletePersonalWorks(OperatingScreenRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("userArtIdList", request.getProductionIdList());
        params.put("userId", request.getUserId());
        params.put("operator", request.getOperator());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(ArtsCentreServiceNameConstants.USER_ART_REMOVE, params, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }
        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }

    /**
     * 根据设备ID查询设备详情
     *
     * @param request
     * @return
     */
    @Override
    public ScreenInfoResponse queryUserDevice(OperatingScreenRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("facilityId", request.getDeviceId());
        params.put("userId", request.getUserId());
        params.put("operator", request.getOperator());
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .get(SmartServiceNameConstants.FACILITY_QUERY_USER_FACILITY, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(responseVo.getData()), ScreenInfoResponse.class);
    }

    /**
     * 批量获取图片url
     *
     * @param fileIds
     * @param width
     * @param type    1为正常切图逻辑 2列表中高清图片降档
     * @return
     */
    @Override
    public Map<Integer, String> getUrls(List<Integer> fileIds, Integer width, Integer type) {
        if (type != null && type == 2 && width >= 1080) {
            width = 750;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("unifyFileIds", fileIds);
        params.put("styleType", 0);
        params.put("styleName", AliImageUtil.getImageCompress(2, width, ImageConstant.SIZE_MIDDLE));
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(UnifyfileServiceNameConstants.FILE_GET_URLS, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(responseVo.getData()), new TypeReference<Map<Integer, String>>() {
        });
    }

    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        return builder.build();
    }


    /**
     * 获取图片宽高
     *
     * @param collect
     * @param userId
     * @return
     */
    @Override
    public List<UserArtVo> getImageWidth(List<Integer> collect, Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("imgId", collect);
        params.put("userId", userId);
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(ArtsCentreServiceNameConstants.USER_ART_LIST, params, ResponseVo.class);
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return JsonUtils.json2list(JSON.toJSONString(responseVo.getData()), UserArtVo.class);
    }


    /**
     * 指令下发
     *
     * @param request
     * @return
     */
    @Override
    public boolean send(SendRequest request) {
        ResponseVo<?> responseVo = null;
        try {
            responseVo = strongSercviceCaller
                    .post(SmartServiceNameConstants.DIRECTIVE_SEND, request, ResponseVo.class);
        } catch (Exception e) {
            return false;
        }

        if (responseVo == null || !responseVo.isSuccess()) {
            return false;
        }
        return true;
    }


    private String getMessageByCode(Integer code) {
        String msg = "失败！";
        switch (code) {
            case 100001:
                msg = "该用户已绑定此设备";
                break;
            case 200001:
                msg = "画屏离线，请检查下画屏状态！";
                break;
            case 999999:
                msg = "程序错误";
        }
        return msg;
    }


    /**
     * 根据url来获取文件信息
     *
     * @param url
     * @return
     */
    @Override
    public ImageInfoDto queryImageInfoByUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        url = QiniuImageUtils.rmArgs(url);
        ResponseVo<ImageInfoDto> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(UnifyfileServiceNameConstants.FILE_QUERY_FILE_INFO, url, new org.codehaus.jackson.type.TypeReference<ResponseVo<ImageInfoDto>>() {
            });
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return responseVo.getData();
    }

    /**
     * 根据文件id查询文件信息
     *
     * @param imageId
     * @return
     */
    @Override
    public ImageInfoDto queryFileInfoById(String imageId) {
        if (StringUtils.isBlank(imageId)) {
            return null;
        }
        ResponseVo<ImageInfoDto> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post(UnifyfileServiceNameConstants.FILE_QUERY_FILE_INFO_BY_ID, imageId, new org.codehaus.jackson.type.TypeReference<ResponseVo<ImageInfoDto>>() {
            });
        } catch (Exception e) {
            return null;
        }
        if (responseVo == null || !responseVo.isSuccess() || responseVo.getData() == null) {
            return null;
        }
        return responseVo.getData();
    }
}
