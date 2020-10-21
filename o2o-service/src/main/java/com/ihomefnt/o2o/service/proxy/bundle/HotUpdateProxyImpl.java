package com.ihomefnt.o2o.service.proxy.bundle;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.bundle.dto.AppVersionDto;
import com.ihomefnt.o2o.intf.domain.bundle.vo.request.AppVersionPageRequestVo;
import com.ihomefnt.o2o.intf.domain.bundle.vo.request.QueryGroupAppRequestVo;
import com.ihomefnt.o2o.intf.domain.bundle.vo.request.UpdateQueryRequest;
import com.ihomefnt.o2o.intf.domain.bundle.vo.request.UploadLogRequest;
import com.ihomefnt.o2o.intf.domain.bundle.vo.response.AppGroupDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.bundle.vo.response.AppInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.bundle.vo.response.AppVersionResponseVo;
import com.ihomefnt.o2o.intf.domain.bundle.vo.response.HotUpdateResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.MobileApiServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.proxy.bundle.HotUpdateProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author hua
 * @Date 2019-07-09 14:03
 */
@Service
public class HotUpdateProxyImpl implements HotUpdateProxy {
    @Autowired
    private StrongSercviceCaller strongSercviceCaller;


    @Override
    public HttpBaseResponse<HotUpdateResponse> queryUpdate(UpdateQueryRequest request) {
        return strongSercviceCaller.post(MobileApiServiceNameConstants.HOT_UPDATE_QUERY_UPDATE, request, new TypeReference<HttpBaseResponse<HotUpdateResponse>>(){});
    }

    @Override
    public HttpBaseResponse uploadLog(UploadLogRequest request) {
        return strongSercviceCaller.post(MobileApiServiceNameConstants.HOT_UPDATE_UPLOAD_LOG, request, HttpBaseResponse.class);
    }

    @Override
    public HotUpdateResponse queryHotUpdateNew(UpdateQueryRequest request) {
        String appVersionCode = String.valueOf(request.getAppVersionCode());
        String appVersionCodeNew = "";
        if (appVersionCode != null && appVersionCode.length() > 1) {
            int len = appVersionCode.length();
            for (int i = 0;i < len-1;i++) {
                appVersionCodeNew += appVersionCode.charAt(i)+"0";
            }
            appVersionCodeNew += appVersionCode.charAt(len-1);
        }
        request.setAppVersionCode(Integer.parseInt(appVersionCodeNew));

        HttpBaseResponse<HotUpdateResponse> response = strongSercviceCaller.post(MobileApiServiceNameConstants.HOT_UPDATE_QUERY_HOT_UPDATE_NEW, request,
                new TypeReference<HttpBaseResponse<HotUpdateResponse>>(){});

        if (null == response) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_NULL, MessageConstant.FAILED);
        }

        return response.getObj();
    }

    @Override
    public AppVersionDto getLatestApp(String parterValue, String appId) {
        JSONObject param = new JSONObject();
        param.put("partnerValue", parterValue);
        param.put("appId", appId);
        try {
            HttpBaseResponse<AppVersionDto> response = strongSercviceCaller.post(MobileApiServiceNameConstants.VERSION_QUERY_LATEST_APP, param,
                    new TypeReference<HttpBaseResponse<AppVersionDto>>() {
                    });

            if (null != response && response.getObj() != null) {
                return response.getObj();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public PageResponse<AppVersionResponseVo> queryAppVersionList(AppVersionPageRequestVo request) {
        HttpBaseResponse<PageResponse<AppVersionResponseVo>> response = strongSercviceCaller.post(MobileApiServiceNameConstants.QUERY_APP_VERSION_LIST, request,
                new TypeReference<HttpBaseResponse<PageResponse<AppVersionResponseVo>>>(){});

        if (null == response || !response.isSuccess()) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_SUCCESS_FALSE, MessageConstant.FAILED);
        }

        return response.getObj();
    }

    @Override
    public AppInfoResponseVo queryAppDetail(String appId) {
        HttpBaseResponse response = strongSercviceCaller.get(MobileApiServiceNameConstants.QUERY_APP_DETAIL, appId, HttpBaseResponse.class);

        if (null == response || !response.isSuccess()) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_SUCCESS_FALSE, MessageConstant.FAILED);
        }

        return JsonUtils.json2obj(JsonUtils.obj2json(response.getObj()), AppInfoResponseVo.class);
    }

    @Override
    public AppGroupDetailResponseVo queryAppGroupDetail(QueryGroupAppRequestVo request) {
        HttpBaseResponse<AppGroupDetailResponseVo> response = strongSercviceCaller.post(MobileApiServiceNameConstants.QUERY_APP_GROUP_DETAIL, request,
                new TypeReference<HttpBaseResponse<AppGroupDetailResponseVo>>(){});

        if (null == response || !response.isSuccess()) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_SUCCESS_FALSE, MessageConstant.FAILED);
        }

        return response.getObj();
    }

    @Override
    public void downloadCount(String appId) {
        HttpBaseResponse response = strongSercviceCaller.post(MobileApiServiceNameConstants.APP_DOWNLOAD_COUNT, appId, HttpBaseResponse.class);

        if (null == response || !response.isSuccess()) {
            throw new BusinessException(HttpResponseCode.SERVICE_RESPONSE_SUCCESS_FALSE, MessageConstant.FAILED);
        }
    }

}
