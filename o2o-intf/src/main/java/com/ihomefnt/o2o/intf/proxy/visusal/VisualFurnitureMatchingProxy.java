package com.ihomefnt.o2o.intf.proxy.visusal;

import com.ihomefnt.o2o.intf.domain.visusal.vo.request.QuerySkuVisiblePicListRequest;

import java.util.List;

public interface VisualFurnitureMatchingProxy {
    boolean supportSKU(Integer solutionId);

    List<String> spaceImgs(QuerySkuVisiblePicListRequest  querySkuVisiblePicListDto);
}
