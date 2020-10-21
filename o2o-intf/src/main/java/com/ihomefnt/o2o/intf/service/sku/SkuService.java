package com.ihomefnt.o2o.intf.service.sku;

import com.ihomefnt.o2o.intf.domain.sku.vo.request.QueryConditionByLastIdRequestVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.request.QueryReplaceSkuByConditionsRequestVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.request.SkuDetailQueryRequestVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.response.QueryConditionByLastIdResponseVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.response.QueryReplaceSkuByConditionsResponseVo;
import com.ihomefnt.o2o.intf.domain.sku.vo.response.QuerySkuDetailResponseVo;

import java.util.List;

public interface SkuService {

    /**
     * 查询SKU详情
     *
     * @param request
     * @return
     */
    QuerySkuDetailResponseVo querySkuDetail(SkuDetailQueryRequestVo request);

    /**
     * 根据末级类目查询过滤条件
     *
     * @param request
     * @return
     */
    QueryConditionByLastIdResponseVo queryConditionByLastCategoryId(QueryConditionByLastIdRequestVo request);

    /**
     * 搜索同类SKU
     *
     * @param request
     * @return
     */
    QueryReplaceSkuByConditionsResponseVo queryReplaceSkuByConditions(QueryReplaceSkuByConditionsRequestVo request);

//    /**
//     * 批量查询sku详情
//     *
//     * @param request
//     * @return
//     */
//    List<QuerySkuDetailResponseVo> skuDetailList(SkuDetailQueryRequestVo request);
}
