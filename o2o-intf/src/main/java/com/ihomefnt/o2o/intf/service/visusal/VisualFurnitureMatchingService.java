package com.ihomefnt.o2o.intf.service.visusal;

import com.ihomefnt.o2o.intf.domain.visusal.vo.request.QuerySkuVisiblePicListRequest;
import com.ihomefnt.o2o.intf.domain.visusal.vo.response.IsSupportSkuResponseVo;
import com.ihomefnt.o2o.intf.domain.visusal.vo.response.VisusalSpaceImgsResponseVo;

public interface VisualFurnitureMatchingService {
    IsSupportSkuResponseVo supportSKU(Integer solutionId);

    VisusalSpaceImgsResponseVo spaceImgs(QuerySkuVisiblePicListRequest request);
}
