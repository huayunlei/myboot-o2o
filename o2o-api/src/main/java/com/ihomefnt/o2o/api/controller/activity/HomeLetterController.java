package com.ihomefnt.o2o.api.controller.activity;

import com.ihomefnt.o2o.intf.domain.activity.vo.request.*;
import com.ihomefnt.o2o.intf.manager.constant.log.LogEnum;
import com.ihomefnt.o2o.intf.proxy.user.LogProxy;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.service.activity.HomeLetterService;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.ActivityInfoResponse;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.ArticleInfoResponse;
import com.ihomefnt.o2o.intf.domain.activity.vo.response.PublishArticleResponseVo;
import com.ihomefnt.oms.trade.util.PageModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * 1219活动  三行家书
 *
 * @author ZHAO
 */
@Deprecated
@RestController
@ApiIgnore
@Api(tags = "【活动】1219三行家书API",hidden = true)
@RequestMapping("/homeLetter")
public class HomeLetterController {

    @Autowired
    private HomeLetterService homeLetterService;

    @Autowired
    private LogProxy logProxy;

    @ApiOperation(value = "发表文章接口", notes = "发表文章接口")
    @RequestMapping(value = "/publishArticle", method = RequestMethod.POST)
    public HttpBaseResponse<PublishArticleResponseVo> publishArticle(@RequestBody PublishArticleRequest request) {
        if (request == null || StringUtils.isBlank(request.getOpenId()) || StringUtils.isBlank(request.getContent())) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        PublishArticleResponseVo obj = homeLetterService.publishArticle(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "投票接口", notes = "投票接口")
    @RequestMapping(value = "/homeLetterVote", method = RequestMethod.POST)
    public HttpBaseResponse<Void> homeLetterVote(@RequestBody HomeLetterVoteRequest request) {
        if (request == null || request.getArticleId() == null || StringUtils.isBlank(request.getOpenId())) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        homeLetterService.homeLetterVote(request);
        return HttpBaseResponse.success();
    }

    @ApiOperation(value = "查询投票记录（分页）接口", notes = "查询投票记录（分页）接口")
    @RequestMapping(value = "/queryVoteRecordList", method = RequestMethod.POST)
    public HttpBaseResponse<PageModel> queryVoteRecordList(@RequestBody QueryVoteRecordRequest request) {
        if (request == null || request.getArticleId() == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        PageModel obj = homeLetterService.queryVoteRecordList(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "查询用户投稿信息接口", notes = "查询用户投稿信息接口")
    @RequestMapping(value = "/queryArticleInfo", method = RequestMethod.POST)
    public HttpBaseResponse<ArticleInfoResponse> queryArticleInfo(@RequestBody ArticleInfoRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }
        ArticleInfoResponse obj = homeLetterService.queryArticleInfo(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "查询所有投稿文章列表（分页）接口", notes = "查询所有投稿文章列表（分页）接口")
    @RequestMapping(value = "/queryArticleList", method = RequestMethod.POST)
    public HttpBaseResponse<PageModel> queryArticleList(@RequestBody ArticleListRequest request) {
        if (request == null) {
            return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
        }

        PageModel obj = homeLetterService.queryArticleList(request);
        return HttpBaseResponse.success(obj);
    }

    @ApiOperation(value = "记录浏览日志接口", notes = "记录浏览日志接口")
    @RequestMapping(value = "/addVistLog", method = RequestMethod.POST)
    public HttpBaseResponse<Void> addVistLog(@RequestBody HomeLetterVisitRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("commonValue", request.getOpenId());
        params.put("visitType", request.getVisitType());
        params.put("action", LogEnum.getMsg(request.getVisitType()));
        params.put("businessCode", request.getArticleId());
        logProxy.addLog(params);

        return HttpBaseResponse.success();
    }

    @ApiOperation(value = "查询活动信息接口", notes = "查询活动信息接口")
    @RequestMapping(value = "/queryActivityInfo", method = RequestMethod.POST)
    public HttpBaseResponse<ActivityInfoResponse> queryActivityInfo(@RequestBody ActivityInfoRequest request) {
        ActivityInfoResponse activityInfoResponse = homeLetterService.queryActivityInfo(request);
        return HttpBaseResponse.success(activityInfoResponse);
    }

}
