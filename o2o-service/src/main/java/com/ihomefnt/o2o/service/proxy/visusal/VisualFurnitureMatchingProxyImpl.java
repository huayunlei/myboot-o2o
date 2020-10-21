package com.ihomefnt.o2o.service.proxy.visusal;

import com.ihomefnt.o2o.intf.domain.visusal.vo.request.QuerySkuVisiblePicListRequest;
import com.ihomefnt.o2o.intf.manager.constant.proxy.DollyWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.response.ResponseVo;
import com.ihomefnt.o2o.intf.proxy.visusal.VisualFurnitureMatchingProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisualFurnitureMatchingProxyImpl implements VisualFurnitureMatchingProxy {
    @Autowired
    private StrongSercviceCaller strongSercviceCaller;

    @Override
    public boolean supportSKU(Integer solutionId) {
        ResponseVo<Boolean> res = strongSercviceCaller.post(DollyWebServiceNameConstants.CHECK_SOLUTION_SKU_VISIBLE, solutionId,
                new TypeReference<ResponseVo<Boolean>>() {
                });

        if (res.isSuccess() && res.getData() != null && res.getData()) {
            return true;
        }

        return false;
    }

    @Override
    public List<String> spaceImgs(QuerySkuVisiblePicListRequest querySkuVisiblePicListDto) {
        return ((ResponseVo<List<String>>) strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_SKU_VISIBLE_PIC_LIST, querySkuVisiblePicListDto,
                new TypeReference<ResponseVo<List<String>>>() {
                })).getData();
    }
}
