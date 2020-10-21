package com.ihomefnt.o2o.api.controller.home;

import com.ihomefnt.o2o.intf.domain.bundle.vo.request.VersionBundleRequestVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homepage.vo.request.*;
import com.ihomefnt.o2o.intf.domain.homepage.vo.response.*;
import com.ihomefnt.o2o.intf.service.home.HomeV510PageService;
import com.ihomefnt.o2o.intf.service.home.HomeV5PageService;
import com.ihomefnt.o2o.service.service.home.HomePageService;
import com.ihomefnt.oms.trade.util.PageModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * App4.0 首页相关接口
 *
 * @author jiangjun
 * @version 2.0, 2018-04-11 下午2:22
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@ApiIgnore
@Deprecated
@Api(tags = "【首页API】", hidden = true)
@RequestMapping("/homePage")
@RestController
public class HomePageController {

    @Autowired
    HomePageService homePageService;

    @Autowired
    HomeV5PageService homeV5PageService;

    @Autowired
    HomeV510PageService homeV510PageService;

    @ApiOperation(value = "首页接口", notes = "首页接口")
    @RequestMapping(value = "/main", method = RequestMethod.POST)
    @Deprecated
    public HttpBaseResponse<HomePageDataVo> homePage(@RequestBody HomePageRequest request) {
        HomePageDataVo homePageData = homePageService.getHomePageData(request);
        return HttpBaseResponse.success(homePageData);
    }

    @ApiOperation(value = "5.0版本首页框架接口", notes = "首页框架接口")
    @RequestMapping(value = "/v5/mainFrame", method = RequestMethod.POST)
    @Deprecated
    public HttpBaseResponse<HomeFrameResponse> homeFrame(@RequestBody HomeFrameRequest request) {
        return HttpBaseResponse.fail(HttpReturnCode.O2O_NEED_FORCE_UPDATE,MessageConstant.MUST_UPDATE_APP);
    }

    @ApiOperation(value = "5.1.0版本首页框架接口", notes = "5.1.0首页框架接口")
    @RequestMapping(value = "/v510/mainFrame", method = RequestMethod.POST)
    @Deprecated
    public HttpBaseResponse<HomeFrameResponse> mainFrame(@RequestBody HomeFrameRequest request) {
        HomeFrameResponse homeFrameResponse = homeV510PageService.getHomePageData(request);
        return HttpBaseResponse.success(homeFrameResponse);
    }

    @ApiOperation(value = "5.0版本首页了解我们", notes = "了解我们")
    @RequestMapping(value = "/v5/aboutUs", method = RequestMethod.POST)
    public HttpBaseResponse<AboutUsResponse> getAboutUsInfo(@RequestBody VersionBundleRequestVo request) {
        AboutUsResponse response = homeV5PageService.getAboutUsInfo(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "5.0版本订单用户评论", notes = "订单用户评论")
    @RequestMapping(value = "/v5/queryUserComment", method = RequestMethod.POST)
    public HttpBaseResponse<PageModel> queryUserComment(@RequestBody UserCommentRequest request) {
        PageModel response = homeV5PageService.getUserCommentList(request);
        return HttpBaseResponse.success(response);
    }

    @ApiOperation(value = "5.0版本首页查询选方案基础信息", notes = "选方案基础信息")
    @RequestMapping(value = "/v5/querySelectBaseData", method = RequestMethod.POST)
    public HttpBaseResponse<OrderCommentResponse> querySelectBaseData(@RequestBody OrderBaseRequest request) {
        OrderCommentResponse response = new OrderCommentResponse();
        return HttpBaseResponse.success(response);
    }

    /**
     * 已废弃
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询已选方案草稿", notes = "已选方案草稿")
    @RequestMapping(value = "/v5/querySolutionDraft", method = RequestMethod.POST)
    public HttpBaseResponse<SolutionDraftResponse> querySolutionDraft(@RequestBody QueryDraftRequest request) {
        SolutionDraftResponse response = homeV5PageService.querySolutionDraft(request);
        return HttpBaseResponse.success(response);
    }
}
