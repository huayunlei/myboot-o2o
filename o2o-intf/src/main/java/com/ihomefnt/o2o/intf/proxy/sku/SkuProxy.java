package com.ihomefnt.o2o.intf.proxy.sku;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.optional.dto.QueryProductDtoBySkuIdsDto;
import com.ihomefnt.o2o.intf.domain.product.dto.QueryConditionByCategoryDto;
import com.ihomefnt.o2o.intf.domain.product.dto.SearchReplaceSkuResponseDto;

import java.util.List;

/**
 * @author jerfan cang
 */
public interface SkuProxy {


    /**
     * 根据sku的id查询其详细信息
     * 包括{id,品类，对客名称，描述，图片url，工艺(仅硬装),属性，品牌(封装到属性里)}
     *
     * @param skuId sku ID
     * @return the detail of sku
     * @throws Exception when info is wrong
     */
    JSONObject loadSkuDetail(Integer skuId, Integer width) throws Exception;

    /**
     * 根据sku的id查询其详细信息针对可定制的sku
     * @param param
     * @return
     */
    List<QueryProductDtoBySkuIdsDto> queryProductDtoBySkuIds(Object param);

    /**
     * 根据类目查筛选条件
     * @param param
     * @return
     */
    QueryConditionByCategoryDto queryConditionByCategory(Object param);

    /**
     * 搜索同类SKU
     * @param param
     * @return
     */
    SearchReplaceSkuResponseDto queryReplaceSkuByConditions(Object param);

}
