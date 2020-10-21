package com.ihomefnt.o2o.api.controller.staticdata;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.AboutAiJiaDataResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.FamilyOrderServiceProcessDataResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.ProductServiceDataResponse;
import com.ihomefnt.o2o.intf.domain.staticdata.vo.response.UserStoryDataResponse;
import com.ihomefnt.o2o.intf.service.staticdata.StaticPageDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 静态页面数据接口
 *
 * @author liyonggang
 * @create 2019-11-15 11:16
 */
@Api(tags = "【静态页面数据API】")
@RestController
@RequestMapping("/staticPageData")
public class StaticPageDataController {

    @Autowired
    private StaticPageDataService staticPageDataService;

    /**
     * 全品家服务流程数据API
     *
     * @param request
     * @return
     */
    @ApiOperation("全品家服务流程API")
    @PostMapping("/getFamilyOrderServiceProcessData")
    public HttpBaseResponse<FamilyOrderServiceProcessDataResponse> getFamilyOrderServiceProcessData(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(staticPageDataService.getFamilyOrderServiceProcessData(request));
    }

    /**
     * 用户故事页面数据
     *
     * @param request
     * @return
     */
    @ApiOperation("用户故事页面数据")
    @PostMapping("/getUserStoryData")
    public HttpBaseResponse<UserStoryDataResponse> getUserStoryData(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(staticPageDataService.getUserStoryData(request));
    }

    /**
     * 用户故事页面数据
     *
     * @param request
     * @return
     */
    @ApiOperation("关于艾佳页面数据")
    @PostMapping("/getAboutAiJiaData")
    public HttpBaseResponse<AboutAiJiaDataResponse> getAboutAiJiaData(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(staticPageDataService.getAboutAiJiaData(request));
    }

    /**
     * 产品服务页面数据
     *
     * @param request
     * @return
     */
    @ApiOperation("产品服务页面数据")
    @PostMapping("/getProductServiceData")
    public HttpBaseResponse<ProductServiceDataResponse> getProductServiceData(@RequestBody HttpBaseRequest request) {
        return HttpBaseResponse.success(staticPageDataService.getProductServiceData(request));
    }
}
