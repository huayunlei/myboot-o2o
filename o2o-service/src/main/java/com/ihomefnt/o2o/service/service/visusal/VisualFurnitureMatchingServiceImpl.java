package com.ihomefnt.o2o.service.service.visusal;

import com.ihomefnt.o2o.intf.domain.visusal.vo.request.QuerySkuVisiblePicListRequest;
import com.ihomefnt.o2o.intf.domain.visusal.vo.response.IsSupportSkuResponseVo;
import com.ihomefnt.o2o.intf.domain.visusal.vo.response.VisusalSpaceImgsResponseVo;
import com.ihomefnt.o2o.intf.proxy.visusal.VisualFurnitureMatchingProxy;
import com.ihomefnt.o2o.intf.service.visusal.VisualFurnitureMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author jiangjun
 * @version 2.0, 2018-04-26 下午1:43
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class VisualFurnitureMatchingServiceImpl implements VisualFurnitureMatchingService {

    @Autowired
    VisualFurnitureMatchingProxy visualFurnitureMatchingProxy;

    @Override
    public IsSupportSkuResponseVo supportSKU(Integer solutionId) {
        boolean flag = visualFurnitureMatchingProxy.supportSKU(solutionId);
        return new IsSupportSkuResponseVo().setSupportSKU(flag);
    }

    @Override
    public VisusalSpaceImgsResponseVo spaceImgs(QuerySkuVisiblePicListRequest request) {
        return new VisusalSpaceImgsResponseVo(visualFurnitureMatchingProxy.spaceImgs(request));
    }
}
