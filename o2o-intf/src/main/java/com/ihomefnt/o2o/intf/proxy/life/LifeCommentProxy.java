package com.ihomefnt.o2o.intf.proxy.life;

import com.ihomefnt.o2o.intf.domain.life.dto.*;
import com.ihomefnt.o2o.intf.domain.life.vo.response.LifeCommentListResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;

import java.util.List;

/**
 * @author liyonggang
 * @create 2018-11-02 9:41
 */
public interface LifeCommentProxy {
    /**
     * 获取生活板块文章评论列表
     * @param request
     * @return
     */
	PageResponse<LifeCommentListResponse> getLifeCommentList(LifeCommentListDto request);

    LifeCommentListResponse publishComment(PublishCommentDto request);

    Integer toPraised(PublishCommentDto request);

    Integer deleteComment(PublishCommentDto request);

    LifeSimpleInfoDto articleSimpleInfo(PublishCommentDto request);

    List<ReplyDto> getCommentReListByCommentIds(List<Long> commentIds);
    
    public List<UserRightsDto> getUsersTopRight(List<Integer> userIds);
}
