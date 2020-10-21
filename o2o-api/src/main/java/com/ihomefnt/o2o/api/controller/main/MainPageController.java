package com.ihomefnt.o2o.api.controller.main;

import com.ihomefnt.o2o.intf.domain.main.vo.request.MainFrameRequest;
import com.ihomefnt.o2o.intf.domain.main.vo.response.MainPageNewResponse;
import com.ihomefnt.o2o.intf.service.main.MainCoreService;
import com.ihomefnt.o2o.intf.service.main.MainPageService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.request.NodeContentRequest;
import com.ihomefnt.o2o.intf.domain.main.vo.response.ContentResponse;
import com.ihomefnt.o2o.intf.domain.main.vo.response.MainPageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiamingyu
 * @date 2019/3/20
 */

@Api(tags = "【首页API】")
@RestController
@RequestMapping("/mainPage")
public class MainPageController {

    @Autowired
    private MainPageService mainPageService;

    @Autowired
    private MainCoreService mainCoreService;

    @Deprecated
    @ApiOperation(value = "5.3.0版本首页接口", notes = "5.3.0首页接口")
    @PostMapping(value = "/v530/mainFrame")
    public HttpBaseResponse<MainPageResponse> mainFrame(@RequestBody MainFrameRequest request) {
        MainPageResponse mainPageResponse = mainPageService.getMainFrameData(request);
        return HttpBaseResponse.success(mainPageResponse);
    }

    @Deprecated
    @ApiOperation(value = "5.3.0版本刷新节点信息接口", notes = "5.3.0版本刷新节点信息接口")
    @PostMapping(value = "/v530/getNodeContent")
    public HttpBaseResponse<ContentResponse> getNodeContent(@RequestBody NodeContentRequest request) {
        ContentResponse contentResponse = mainPageService.getNodeContent(request);
        return HttpBaseResponse.success(contentResponse);
    }

    @ApiOperation(value = "6.0.0核心操作区接口", notes = "6.0.0核心操作区接口")
    @PostMapping(value = "/v600/mainCore")
    public HttpBaseResponse<MainPageNewResponse> mainCore(@RequestBody MainFrameRequest request) {
        MainPageNewResponse contentResponse = mainCoreService.getMainCore(request);
        return HttpBaseResponse.success(contentResponse);
    }

}
