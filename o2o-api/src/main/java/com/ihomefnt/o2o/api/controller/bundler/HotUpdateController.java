package com.ihomefnt.o2o.api.controller.bundler;

import com.ihomefnt.o2o.intf.domain.bundle.vo.request.UpdateQueryRequest;
import com.ihomefnt.o2o.intf.domain.bundle.vo.request.UploadLogRequest;
import com.ihomefnt.o2o.intf.domain.bundle.vo.response.HotUpdateResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.manager.constant.bundle.BundleConstant;
import com.ihomefnt.o2o.intf.proxy.bundle.HotUpdateProxy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author hua
 * @Date 2019-07-09 13:59
 */
@Api(tags = "【版本升级相关API】")
@RestController
@RequestMapping("/hotUpdate")
public class HotUpdateController {

    @Autowired
    private HotUpdateProxy hotUpdateProxy;

    @ApiOperation("app查询热更新")
    @PostMapping(value = "/queryUpdate")
    public HttpBaseResponse<HotUpdateResponse> queryUpdate(@RequestBody UpdateQueryRequest request) {
        return hotUpdateProxy.queryUpdate(request);
    }

    @ApiOperation("上传更新日志")
    @PostMapping(value = "/uploadLog")
    public HttpBaseResponse uploadLog(@RequestBody UploadLogRequest request) {
        return hotUpdateProxy.uploadLog(request);
    }

    @ApiOperation("app查询热更新")
    @PostMapping(value = "/queryHotUpdateNew")
    public HttpBaseResponse<HotUpdateResponse> queryHotUpdateNew(@RequestBody UpdateQueryRequest request) {
        HotUpdateResponse obj = hotUpdateProxy.queryHotUpdateNew(request);
        return HttpBaseResponse.success(obj, BundleConstant.BUNDLE_LATEST);
    }

}
