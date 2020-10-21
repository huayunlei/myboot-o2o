package com.ihomefnt.o2o.intf.proxy.sku;

import com.alibaba.fastjson.JSONObject;

/**
 * @author jerfan cang
 * @date 2018/8/31 9:38
 */
public interface SkuSearchProxy {


    /**
     * 根据二级类目和四级类目的id查询检索条件
     *   三级类目(包含四级类目)
     *   品牌
     *   规格尺寸
     * @param levTwo 二级类目id
     * @param levFour 四级类目id
     * @return obj
     * @throws Exception server exception
     */
    JSONObject queryConditionsByCategoryId(Integer levTwo,Integer levFour) throws RuntimeException;


    /**
     * 条件化查询更多软装可替换sku
     * @param reqBean 请求参数
     * @return obj 参考wiki http://wiki.ihomefnt.com/pages/viewpage.action?pageId=19367020
     * @throws Exception server exception
     */
    JSONObject queryMoreSkuByCondition(JSONObject reqBean)throws RuntimeException;
}
