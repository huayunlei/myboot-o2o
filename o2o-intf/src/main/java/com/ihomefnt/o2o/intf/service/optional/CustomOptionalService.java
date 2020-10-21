package com.ihomefnt.o2o.intf.service.optional;

import com.ihomefnt.o2o.intf.domain.optional.vo.request.CreateCustomSkuRequestVo;
import com.ihomefnt.o2o.intf.domain.optional.vo.response.CreateCustomSkuResponseVo;
import com.ihomefnt.o2o.intf.domain.optional.vo.response.CustoOptionalResponseVo;

/**
 * 订制品服务
 *
 * @author liyonggang
 * @create 2018-11-22 19:38
 */
public interface CustomOptionalService {
    /**
     * 查询订制品属性信息
     *
     * @param skuId
     * @return
     */
	CustoOptionalResponseVo queryCustomAttrs(Integer width,Integer skuId);

    /**
     * 新建sku
     *
     * @param request
     * @return
     */
	CreateCustomSkuResponseVo createCustomSku(CreateCustomSkuRequestVo request);
}
