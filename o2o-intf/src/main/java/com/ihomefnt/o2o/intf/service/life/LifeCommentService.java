package com.ihomefnt.o2o.intf.service.life;

import com.ihomefnt.o2o.intf.domain.life.dto.LifeCommentListDto;
import com.ihomefnt.o2o.intf.domain.life.dto.LifeSimpleInfoDto;
import com.ihomefnt.o2o.intf.domain.life.dto.PublishCommentDto;
import com.ihomefnt.o2o.intf.domain.life.vo.response.LifeCommentListResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;

/**
 * @author liyonggang
 * @create 2018-11-01 17:46
 */
public interface LifeCommentService {
    PageResponse<LifeCommentListResponse> getLifeCommentList(LifeCommentListDto request);

    LifeCommentListResponse publishComment(PublishCommentDto request);

    Integer toPraised(PublishCommentDto request);

    Integer deleteComment(PublishCommentDto request);

    LifeSimpleInfoDto articleSimpleInfo(PublishCommentDto request);
}
