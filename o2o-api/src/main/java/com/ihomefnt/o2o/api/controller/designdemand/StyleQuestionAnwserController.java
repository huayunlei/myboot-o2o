package com.ihomefnt.o2o.api.controller.designdemand;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.style.vo.request.QuerySelectedQusAnsRequest;
import com.ihomefnt.o2o.intf.domain.style.vo.response.QuestionAnwserSimpleInfoResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionAnwserStepResponse;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleQuestionSelectedResponse;
import com.ihomefnt.o2o.intf.service.designDemand.StyleQuestionAnwserService;
import com.ihomefnt.o2o.intf.service.home.HomeCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "【设计任务相关】")
@RestController
@RequestMapping("/style")
public class StyleQuestionAnwserController {

    @Autowired
    private StyleQuestionAnwserService styleQuestionAnwserService;

    @Autowired
    HomeCardService homeCardService;

    /**
     * 查询所有问题答案
     */
    @ApiOperation(value = "查询所有问题答案", notes = "查询所有问题答案")
    @PostMapping(value = "/queryAllQuestionAnwserList")
    public HttpBaseResponse<List<StyleQuestionAnwserStepResponse>> queryAllQuestionAnwserList(@RequestBody HttpBaseRequest request) {
        List<StyleQuestionAnwserStepResponse> result = styleQuestionAnwserService.queryAllQuestionAnwserList(request,1);
        return HttpBaseResponse.success(result);
    }

    /**
     * 查询所有问题答案
     */
    @ApiOperation(value = "查询所有问题答案", notes = "查询所有问题答案")
    @PostMapping(value = "/queryAllQuestionAnwserListV2")
    public HttpBaseResponse<List<StyleQuestionAnwserStepResponse>> queryAllQuestionAnwserListV2(@RequestBody HttpBaseRequest request) {
        if (homeCardService.getSpaceMarkMustUpdate(request.getAppVersion(),request.getBundleVersions(),request.getOsType())) {
            return HttpBaseResponse.fail(HttpReturnCode.O2O_NEED_FORCE_UPDATE,MessageConstant.MUST_UPDATE_APP);
        }
        List<StyleQuestionAnwserStepResponse> result = styleQuestionAnwserService.queryAllQuestionAnwserList(request,2);
        return HttpBaseResponse.success(result);
    }

    /**
     * 查询所有问题答案
     */
    @ApiOperation(value = "查询订单已选问题答案详情", notes = "查询订单已选问题答案详情")
    @PostMapping(value = "/queryQuestionAnwserDetail")
    public HttpBaseResponse<List<StyleQuestionSelectedResponse>> queryQuestionAnwserDetail(@RequestBody QuerySelectedQusAnsRequest request) {
        List<StyleQuestionSelectedResponse> result = styleQuestionAnwserService.queryQuestionAnwserDetail(request);
        return HttpBaseResponse.success(result);
    }


    /**
     * 查询所有问题答案
     */
    @ApiOperation(value = "查询设计任务简单信息", notes = "查询设计任务简单信息")
    @PostMapping(value = "/queryQuestionAnwserSimpleInfo")
    public HttpBaseResponse<QuestionAnwserSimpleInfoResponse> queryQuestionAnwserSimpleInfo(@RequestBody QuerySelectedQusAnsRequest request) {
        return HttpBaseResponse.success(styleQuestionAnwserService.queryQuestionAnwserSimpleInfo(request));
    }

}
