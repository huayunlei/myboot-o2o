package com.ihomefnt.o2o.intf.proxy.bundle;

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
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;

/**
 * @Description:
 * @Author hua
 * @Date 2019-07-09 14:02
 */
public interface HotUpdateProxy {
    HttpBaseResponse<HotUpdateResponse> queryUpdate(UpdateQueryRequest request);

    HttpBaseResponse uploadLog(UploadLogRequest request);

    HotUpdateResponse queryHotUpdateNew(UpdateQueryRequest request);

    /**
     * 查询版本
     *
     * @param parterValue
     * @return
     */
    AppVersionDto getLatestApp(String parterValue, String appId);

    PageResponse<AppVersionResponseVo> queryAppVersionList(AppVersionPageRequestVo request);

    AppInfoResponseVo queryAppDetail(String appId);

    AppGroupDetailResponseVo queryAppGroupDetail(QueryGroupAppRequestVo request);

    void downloadCount(String appId);
}
