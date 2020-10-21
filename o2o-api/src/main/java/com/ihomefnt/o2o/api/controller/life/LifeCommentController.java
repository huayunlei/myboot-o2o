package com.ihomefnt.o2o.api.controller.life;

import com.ihomefnt.o2o.intf.domain.life.dto.LifeCommentListDto;
import com.ihomefnt.o2o.intf.domain.life.dto.LifeSimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.life.dto.PublishCommentDto;
import com.ihomefnt.o2o.intf.domain.life.vo.response.LifeCommentListResponse;
import com.ihomefnt.o2o.intf.service.life.LifeCommentService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyonggang
 * @create 2018-11-01 17:37
 */
@Api(tags = "【生活板块API】")
@RestController
@RequestMapping("/lifeComment")
public class LifeCommentController {

    @Autowired
    private LifeCommentService lifeCommentService;
    /**
     * 评论列表
     * @param request
     * @return
     */
    @ApiOperation(value = "评论列表",notes = "分页查询评论")
    @RequestMapping(value = "/getLifeCommentList",method = RequestMethod.POST)
    public HttpBaseResponse<PageResponse<LifeCommentListResponse>> getLifeCommentList(@RequestBody LifeCommentListDto request) {
        return  HttpBaseResponse.success(lifeCommentService.getLifeCommentList(request));
    }
    /**
     * 评论发布
     * @param request
     * @return
     */
    @ApiOperation(value = "评论发布",notes = "发布评论（需要传入评论内容和文章id）")
    @RequestMapping(value = "/publishComment",method = RequestMethod.POST)
    public HttpBaseResponse<LifeCommentListResponse> publishComment(@RequestBody PublishCommentDto request) {
        return  HttpBaseResponse.success(lifeCommentService.publishComment(request));
    }
    /**
     * 评论点赞
     * @param request
     * @return
     */
    @ApiOperation(value = "评论点赞",notes = "评论点赞（传入评论id即可）")
    @RequestMapping(value = "/toPraised",method = RequestMethod.POST)
    public HttpBaseResponse<Void> toPraised(@RequestBody PublishCommentDto request) {
        lifeCommentService.toPraised(request);
        return  HttpBaseResponse.success();
    }
    /**
     * 删除评论
     * @param request
     * @return
     */
    @ApiOperation(value = "删除评论",notes = "删除评论（传入评论id即可）")
    @RequestMapping(value = "/deleteComment",method = RequestMethod.POST)
    public HttpBaseResponse<Void> deleteComment(@RequestBody PublishCommentDto request) {
        lifeCommentService.deleteComment(request);
        return  HttpBaseResponse.success();
    }
    /**
     * 文章简单信息查询
     * @param request
     * @return
     */
    @ApiOperation(value = "文章简单信息查询",notes = "articleSimpleInfo（传入文章id即可）")
    @RequestMapping(value = "/articleSimpleInfo",method = RequestMethod.POST)
    public HttpBaseResponse<LifeSimpleInfoDto> articleSimpleInfo(@RequestBody PublishCommentDto request) {
    	LifeSimpleInfoDto ifeSimpleInfo = lifeCommentService.articleSimpleInfo(request);
    	return HttpBaseResponse.success(ifeSimpleInfo);
    }
}
