package com.ihomefnt.o2o.intf.proxy.optional;

import com.ihomefnt.o2o.intf.domain.optional.dto.CreateCustomSkuRequestDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.CreateCustomSkuResponseDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.CustoOptionalResponseDto;

/**
 * @author liyonggang
 * @create 2018-11-22 19:40
 */
public interface CustomOptionalProxy {
    /**
     * 查询订制品属性信息
     *
     * @param skuId
     * @return
     */
    CustoOptionalResponseDto queryCustomAttrs(Integer skuId);

    /**
     * 新建sku
     *
     * @param request
     * @return
     */
    CreateCustomSkuResponseDto createCustomSku(CreateCustomSkuRequestDto request);
}
