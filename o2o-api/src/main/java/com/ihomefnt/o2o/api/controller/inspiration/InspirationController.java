package com.ihomefnt.o2o.api.controller.inspiration;

import com.ihomefnt.o2o.intf.domain.comment.vo.request.UserCommentRequestVo;
import com.ihomefnt.o2o.intf.domain.comment.vo.response.UserCommendResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Case;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.KeyValue;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Strategy;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.*;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.*;
import com.ihomefnt.o2o.intf.domain.product.doo.TCollection;
import com.ihomefnt.o2o.intf.domain.user.doo.UserVisitLogDo;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.inspiration.InspirationConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.comment.CommentService;
import com.ihomefnt.o2o.intf.service.inspiration.InspirationService;
import com.ihomefnt.o2o.intf.service.product.CollectionService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 所有方法后面带270表示都是APP2.7.0灵感改版增加方法<br/>
 * 其他都是以前版本方法<br/>
 */
@ApiIgnore
@Deprecated
@RestController
@RequestMapping("/inspiration")
@Api(tags = "灵感文章API",hidden = true)
public class InspirationController {

    @Autowired
    InspirationService inspirationService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    CollectionService collectionService;

    /**
     * 获取所有的灵感文章分类
     */
    @ApiOperation(value = "获取所有灵感文章分类（2.9.3）", notes = "getArticleTypeList290")
    @RequestMapping(value = "/getArticleTypeList290", method = RequestMethod.POST)
    public HttpBaseResponse<ArticleTypeListResponseVo> getArticleTypeList290(@Json HttpBaseRequest request) {
        //此list肯定有数据,所以不用保护
        List<KeyValue> list = inspirationService.getArticleTypeList290();
        ArticleTypeListResponseVo response = new ArticleTypeListResponseVo(list);
        return HttpBaseResponse.success(response);
    }

    /**
     * 获取灵感版本改版后的首页 （2.9.3版本增加）
     */
    @ApiOperation(value = "获取灵感版本改版后的首页 （2.9.3版本增加）", notes = "home290")
    @RequestMapping(value = "/home290", method = RequestMethod.POST)
    public HttpBaseResponse<HttpInspirationResponse290> home290(@Json HttpInspirationRequest270 request) {
        HttpInspirationResponse290 response = inspirationService.getHome290(request);
        return HttpBaseResponse.success(response);
    }

    /**
     * 获取所有的灵感文章分类
     */
    @RequestMapping(value = "/getArticleTypeList270", method = RequestMethod.POST)
    public HttpBaseResponse<ArticleTypeListResponseVo> getArticleTypeList270(@Json HttpBaseRequest request) {
        //此list肯定有数据,所以不用保护
        List<KeyValue> list = inspirationService.getArticleTypeList270();
        ArticleTypeListResponseVo response = new ArticleTypeListResponseVo(list);
        return HttpBaseResponse.success(response);
    }

    /**
     * 获取灵感版本改版后的首页
     */
    @RequestMapping(value = "/home270", method = RequestMethod.POST)
    public HttpBaseResponse<HttpInspirationResponse270> home270(@Json HttpInspirationRequest270 request) {
        HttpInspirationResponse270 response = inspirationService.getHome270(request);
        return HttpBaseResponse.success(response);
    }

    /**
     * 获取灵感版本的文章详情
     */
    @RequestMapping(value = "/articleDetail270", method = RequestMethod.POST)
    public HttpBaseResponse<HttpArticleResponse270> articleDetail270(@Json HttpArticleRequest270 request) {
        //检查参数是否为空
        if (request == null || request.getArticleId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        //获取用户信息
        Long userId = null;
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (null != userDto) {
            userId = userDto.getId().longValue();
        }

        HttpArticleResponse270 response = inspirationService.getArticleDetailByPK270(request, userId);
        if (response == null) {
            return HttpBaseResponse.fail(InspirationConstant.RESULT_DATA_EMPTY);
        }
        return HttpBaseResponse.success(response);
    }

    /**
     * 获取灵感版本的文章所有评论
     */
    @RequestMapping(value = "/commentList270", method = RequestMethod.POST)
    public HttpBaseResponse<HttpCommentResponse270> commentList270(@Json HttpArticleRequest270 request) {
        //检查参数是否为空
        if (request == null || request.getArticleId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpCommentResponse270 response = inspirationService.getCommentListByArticleId(request);
        if (response == null) {
            return HttpBaseResponse.fail(InspirationConstant.RESULT_DATA_EMPTY);
        }
        return HttpBaseResponse.success(response);
    }

    /**
     * 点赞
     */
    @RequestMapping(value = "/praise270", method = RequestMethod.POST)
    public HttpBaseResponse<Void> praise270(@Json HttpArticleRequest270 request) {
        //检查参数是否为空
        if (request == null || request.getArticleId() == null || StringUtils.isBlank(request.getAccessToken())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        UserDto userDto = userService.getUserByToken(request.getAccessToken());
        if (userDto == null) {
            return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ILLEGAL_USER);
        }
        Long articleId = request.getArticleId();
        Long userId = userDto.getId().longValue();
        int result = inspirationService.praiseArticle(articleId, userId);
        if (result == InspirationConstant.NUM_PRAISED) {
            return HttpBaseResponse.fail(InspirationConstant.RESULT_PRAISED);
        }
        if (result == InspirationConstant.NUM__PRAISE_WRONG) {
            return HttpBaseResponse.fail(InspirationConstant.RESULT_PRAISE_WRONG);
        }
        if (result == InspirationConstant.NUM_PRAISE_GOOD) {
            return HttpBaseResponse.success(null, InspirationConstant.RESULT_PRAISE_GOOD);
        }
        return HttpBaseResponse.fail(MessageConstant.FAILED);
    }

    /**
     * 转发
     */
    @RequestMapping(value = "/forward270", method = RequestMethod.POST)
    public HttpBaseResponse<Void> forward270(@Json HttpArticleRequest270 request) {
        //检查参数是否为空
        if (request == null || request.getArticleId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        Long articleId = request.getArticleId();
        boolean result = inspirationService.forwardArticle(articleId);
        if (!result) {
            return HttpBaseResponse.fail(InspirationConstant.FORWARD__WRONG);
        }
        return HttpBaseResponse.success(null, InspirationConstant.FORWARD_GOOD);
    }

    /**
     * 收藏
     */
    @RequestMapping(value = "/collect270", method = RequestMethod.POST)
    public HttpBaseResponse<Void> collect270(@Json HttpArticleRequest270 request) {
        //检查参数是否为空
        if (request == null || request.getArticleId() == null) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        Long articleId = request.getArticleId();
        boolean result = inspirationService.collectArticle(articleId);
        if (!result) {
            return HttpBaseResponse.fail(InspirationConstant.COLLECT__WRONG);
        }
        return HttpBaseResponse.success(null, InspirationConstant.COLLECT_GOOD);
    }

    /**
     * 用户增加评论
     */
    @RequestMapping(value = "/comment270", method = RequestMethod.POST)
    public HttpBaseResponse<Void> comment270(@Json HttpCommentRequest270 request) {
        //检查参数是否为空
        if (request == null || request.getArticleId() == null
                || StringUtils.isBlank(request.getComment())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        String comment = request.getComment();
        if (comment.length() > 500) {
            return HttpBaseResponse.fail(InspirationConstant.MSG_DATA_LENGTH_ERROR);
        }
        Long userId = null;
        HttpUserInfoRequest userDto = request.getUserInfo();
        if (null != userDto) {
            userId = userDto.getId().longValue();
        }
        boolean result = inspirationService.addComment(request, userId);
        if (!result) {
            return HttpBaseResponse.fail(InspirationConstant.COMMENT__WRONG);
        }
        return HttpBaseResponse.success(null, InspirationConstant.COMMENT_GOOD);
    }

    /**
     * 搜索灵感文章
     */
    @RequestMapping(value = "/searhArticleList270", method = RequestMethod.POST)
    public HttpBaseResponse<HttpSearchArticleResponse270> searhArticleList270(@Json HttpSearchArticleRequest270 request) {
        // 检查参数是否为空
        if (request == null || StringUtils.isBlank(request.getTitle())) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpSearchArticleResponse270 response = inspirationService.searhArticleList(request);
        return HttpBaseResponse.success(response, InspirationConstant.QUERY_DATA_GOOD);
    }


    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public HttpBaseResponse<HttpInspirationHomeResponse> home(@Json HttpMoreInspirationRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        HttpInspirationHomeResponse response = new HttpInspirationHomeResponse();
        response.setPictureButtonList(inspirationService.queryPhotoButton());
        response.setStrategyButtonList(inspirationService.queryStrategyButton());

        request.setPageNo(1);
        request.setPageSize(3);
        HttpCaseListResponse caseListResponse = inspirationService.queryCaseList(request);
        caseListResponse.setClassifyTreeList(null);
        response.setMoreCaseResponse(caseListResponse);

        return HttpBaseResponse.success(response);
    }

    @RequestMapping(value = "/moreCase", method = RequestMethod.POST)
    public HttpBaseResponse<HttpCaseListResponse> moreCase(
            @Json HttpMoreInspirationRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        //分页查询案例信息
        HttpCaseListResponse response = inspirationService.queryCaseList(request);
        return HttpBaseResponse.success(response);
    }

    @RequestMapping(value = "/caseDetail", method = RequestMethod.POST)
    public HttpBaseResponse<HttpCaseDetailResponse> caseDetail(
            @Json HttpInspirationRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        //查询案例详情信息
        HttpCaseDetailResponse response = inspirationService.queryCaseDetailApi(request);

        UserDto userDto = userService.getUserByToken(request.getAccessToken());
        if (userDto != null && null != userDto.getId()) {
            TCollection collection = collectionService.queryCollection(request.getInspirationId(), userDto.getId().longValue(), 5l);
            if (collection == null || collection.getStatus() == 0) {
                response.setCollection(0);
            } else {
                response.setCollection(1);
            }
        } else {
            response.setCollection(0);
        }

        UserCommentRequestVo commentRequest = new UserCommentRequestVo();
        commentRequest.setPageNo(0);
        commentRequest.setPageSize(2);
        commentRequest.setType(5l);
        commentRequest.setProductId(request.getInspirationId());
        UserCommendResponseVo userCommendResponse = commentService.queryUserCommentList(commentRequest);
        response.setUserCommentList(userCommendResponse.getUserCommentList());
        response.setCommentCount(userCommendResponse.getTotalRecords());

        Case caseInfo = response.getCaseInfo();
        if (null != caseInfo) {
            UserVisitLogDo userVisitLog = new UserVisitLogDo(request.getDeviceToken(), request.getMobileNum(), 6, "阅读案例详情", 20, caseInfo.getCaseId()); //记录用户行为日志
            userService.saveUserVisitLog(userVisitLog);
        }

        return HttpBaseResponse.success(response);
    }

    @RequestMapping(value = "/moreStrategy", method = RequestMethod.POST)
    public HttpBaseResponse<HttpStrategyListResponse> moreStrategy(
            @Json HttpMoreInspirationRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        //分页查询攻略列表
        HttpStrategyListResponse response = inspirationService.queryStrategyList(request);
        return HttpBaseResponse.success(response);
    }

    @RequestMapping(value = "/strategyDetail", method = RequestMethod.POST)
    public HttpBaseResponse<HttpStrategyDetailResponse> strategyDetail(
            @Json HttpInspirationRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        //分页查询攻略信息
        HttpStrategyDetailResponse response = inspirationService.queryStrategyDetailApi(request);

        UserDto userDto = userService.getUserByToken(request.getAccessToken());
        if (userDto != null && null != userDto.getId()) {
            TCollection collection = collectionService.queryCollection(request.getInspirationId(), userDto.getId().longValue(), 4l);
            if (collection == null || collection.getStatus() == 0) {
                response.setCollection(0);
            } else {
                response.setCollection(1);
            }
        } else {
            response.setCollection(0);
        }

        UserCommentRequestVo commentRequest = new UserCommentRequestVo();
        commentRequest.setPageNo(0);
        commentRequest.setPageSize(3);
        commentRequest.setType(4l);
        commentRequest.setProductId(request.getInspirationId());
        UserCommendResponseVo userCommendResponse = commentService.queryUserCommentList(commentRequest);
        response.setUserCommentList(userCommendResponse.getUserCommentList());
        response.setCommentCount(userCommendResponse.getTotalRecords());

        Strategy strategy = response.getStrategy();
        if (null != strategy) {
            UserVisitLogDo userVisitLog = new UserVisitLogDo(request.getDeviceToken(), request.getMobileNum(), 6, "阅读攻略详情", 21, strategy.getStrategyId()); //记录用户行为日志
            userService.saveUserVisitLog(userVisitLog);
        }

        return HttpBaseResponse.success(response);
    }

    @RequestMapping(value = "/pictureAlbumList", method = RequestMethod.POST)
    public HttpBaseResponse<HttpPictureListResponse> pictureAlbumList(
            @Json HttpMoreInspirationRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        //分页查询案例信息
        HttpUserInfoRequest userDto = request.getUserInfo();
        Long userId = null;
        if (null != userDto) {
            userId = userDto.getId().longValue();
        }
        HttpPictureListResponse response = inspirationService.queryPictureAlbumList(request, userId);

        UserVisitLogDo userVisitLog = new UserVisitLogDo(request.getDeviceToken(), request.getMobileNum(), 6, "浏览美图", 22, null); //记录用户行为日志
        userService.saveUserVisitLog(userVisitLog);

        return HttpBaseResponse.success(response);
    }

    @RequestMapping(value = "/pictureAlbumView", method = RequestMethod.POST)
    public HttpBaseResponse<HttpPictureDetailResponse> pictureAlbumView(
            @Json HttpInspirationRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        //查看图片
        HttpPictureDetailResponse response = inspirationService.queryPictureAlbumView(request);
        inspirationService.updateViewCount(request);
        return HttpBaseResponse.success(response);
    }

    @RequestMapping(value = "/updateViewCount", method = RequestMethod.POST)
    public HttpBaseResponse<Void> updateViewCount(
            @Json HttpInspirationRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        //inspirationService.updateViewCount(request);

        return HttpBaseResponse.success();
    }

    @RequestMapping(value = "/updateTranspondCount", method = RequestMethod.POST)
    public HttpBaseResponse<Void> updateTranspondCount(
            @Json HttpInspirationRequest request) {
        if (null == request) {
            return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }

        inspirationService.updateTranspondCount(request);
        return HttpBaseResponse.success();
    }

}
