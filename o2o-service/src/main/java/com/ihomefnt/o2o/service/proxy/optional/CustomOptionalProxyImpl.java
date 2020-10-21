package com.ihomefnt.o2o.service.proxy.optional;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.optional.dto.CreateCustomSkuRequestDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.CreateCustomSkuResponseDto;
import com.ihomefnt.o2o.intf.domain.optional.dto.CustoOptionalResponseDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.ProductWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.optional.CustomOptionalProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 定制品选配
 *
 * @author liyonggang
 * @create 2018-11-22 19:42
 */
@Service
public class CustomOptionalProxyImpl implements CustomOptionalProxy {

    @Autowired
	private StrongSercviceCaller strongSercviceCaller;

    /**
     * 查询订制品属性信息
     *
     * @param skuId
     * @return
     */
    @Override
    public CustoOptionalResponseDto queryCustomAttrs(Integer skuId) {
        if (skuId == null) {
            return null;
        }
        try {
        	ResponseVo<CustoOptionalResponseDto> responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.PRODUCT_APP_QUERY_CUSTOM_ATTR_TREE, skuId, new TypeReference<ResponseVo<CustoOptionalResponseDto>>() {
            });
        	if (responseVo != null && responseVo.isSuccess()) {
        		return responseVo.getData();
        	}
        } catch (Exception e) {
        	return null;
        }
        return null;
    }

    /**
     * 新建sku
     * @param request
     * @return
     */
    @Override
    public CreateCustomSkuResponseDto createCustomSku(CreateCustomSkuRequestDto request) {
        try {
        	ResponseVo<CreateCustomSkuResponseDto> responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.PRODUCT_APP_CREATE_CUSTOM_SKU, request, new TypeReference<ResponseVo<CreateCustomSkuResponseDto>>() {
            });
        	if (responseVo != null && responseVo.isSuccess()) {
        		return responseVo.getData();
        	}
        } catch (Exception e) {
        	return null;
        }
        return null;
    }
}
