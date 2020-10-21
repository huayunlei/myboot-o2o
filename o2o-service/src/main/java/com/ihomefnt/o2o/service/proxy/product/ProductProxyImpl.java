/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年12月7日
 * Description:ProductProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.product;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.Page;
import com.ihomefnt.o2o.intf.domain.product.dto.*;
import com.ihomefnt.o2o.intf.domain.program.dto.ReplaceAbleDto;
import com.ihomefnt.o2o.intf.domain.program.dto.RoomClassIdDto;
import com.ihomefnt.o2o.intf.domain.program.vo.response.ReplaceAbleDollyResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.ProductWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.product.ProductProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 */
@Service
public class ProductProxyImpl implements ProductProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public ProductListResponseVo queryOnShelveAndWithPicSku(Object param) {
		ResponseVo<ProductListResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_ON_SHELVE_AND_WITH_PIC_SKU, param,
					new TypeReference<ResponseVo<ProductListResponseVo>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public ProductResponseVo querySimpleSkuById(Object param) {
		ResponseVo<ProductResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_SIMPLE_SKU_BY_ID, param,
					new TypeReference<ResponseVo<ProductResponseVo>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public List<BrandResponeVo> getSkuBrandInfo(Object param) {
		ResponseVo<List<BrandResponeVo>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.GET_SKU_BRAND_INFO, param,
					new TypeReference<ResponseVo<List<BrandResponeVo>>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public ShelveListResponeVo queryShelveSku(Object param) {
		ResponseVo<ShelveListResponeVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_SHELVE_SKU, param,
					new TypeReference<ResponseVo<ShelveListResponeVo>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public ProductListResponseVo querySimpleSkuSelective(Object param) {
		ResponseVo<ProductListResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_SIMPLE_SKU_SELECTIVE, param,
					new TypeReference<ResponseVo<ProductListResponseVo>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public List<ProductResponseVo> querySimpleSku(Object param) {
		ResponseVo<List<ProductResponseVo>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_SIMPLE_SKU, param,
					new TypeReference<ResponseVo<List<ProductResponseVo>>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}


	@Override
	public TripProductDto queryProductById(Integer itemId) {
		ResponseVo<TripProductDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_PRODUCT_BY_ID, itemId,
					new TypeReference<ResponseVo<TripProductDto>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}


	@Override
	public SellerVo getSellerByProductId(Map<String, Object> param) {
		ResponseVo<SellerVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.GET_SELLER_BY_PRODUCT_ID, param,
					new TypeReference<ResponseVo<SellerVo>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}


	@Override
	public boolean generatorOrderCode(GenerateCodeVo param) {
		ResponseVo<?> responseVo = null;
		try {
			responseVo = strongSercviceCaller
					.post(ProductWebServiceNameConstants.GENERATOR_ORDER_CODE, param, ResponseVo.class);
		} catch (Exception e) {
			return false;
		}

		if (null != responseVo) {
			return responseVo.isSuccess();
		}
	
		return false;
	}


	@Override
	public Page<TripProductDto> queryTripProductList(Map<String, Object> param) {
		ResponseVo<Page<TripProductDto>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_TRIP_PRODUCT_LIST, param,
						new TypeReference<ResponseVo<Page<TripProductDto>>>() {
				});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}


	@Override
	public List<ConsumeCodeVo> getCodeListByOrderId(Map<String, Object> param) {
		ResponseVo<List<ConsumeCodeVo>> responseVo = null;
		try {
			responseVo = strongSercviceCaller
					.post(ProductWebServiceNameConstants.GET_CODE_LIST_BY_ORDER_ID, param,
					new TypeReference<ResponseVo<List<ConsumeCodeVo>>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}


	@Override
	public boolean useCode(Map<String, Object> param) {
		ResponseVo<?> responseVo = null;
		try {
			responseVo = strongSercviceCaller
					.post(ProductWebServiceNameConstants.TRIP_PRODUCT_CODE_USE_CODE, param, ResponseVo.class);
		} catch (Exception e) {
			return false;
		}
		if (null != responseVo) {
			return responseVo.isSuccess();
		}
	
		return false;
	}
	
	
	@Override
	public Map<String, ProductCategoryVo> batchQueryCategoryFullPathByIds(List<Integer> productCategoryIds) {
		ResponseVo<Map<String, ProductCategoryVo>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.BATCH_QUERY_CATEGORY_FULL_PATH_BY_IDS, productCategoryIds,
					new TypeReference<ResponseVo<Map<String, ProductCategoryVo>>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public List<RoomClassIdDto> batchQueryClassIdBySkuId(List<Integer> skuIdList) {
		ResponseVo<List<RoomClassIdDto>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.BATCH_QUERY_CLASSID_BY_SKU_ID, skuIdList,
					new TypeReference<ResponseVo<List<RoomClassIdDto>>>() {
			});
		} catch (Exception e) {
			return null;
		}
		
		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public List<SkuBaseInfoDto> batchQuerySkuBaseInfo(List<Integer> skuIdList) {
		ResponseVo<List<SkuBaseInfoDto>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_SKUS_BASE_INFO, skuIdList,
					new TypeReference<ResponseVo<List<SkuBaseInfoDto>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public BomCategoryListDto batchQuerySkuBomBaseInfo(List<Integer> skuIdList) {
		ResponseVo<BomCategoryListDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.QUERY_BOM_BASE_INFO, skuIdList,
					new TypeReference<ResponseVo<BomCategoryListDto>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	/**
	 * 一键替换接口
	 * @param request
	 * @return
	 */
	@Override
	public ReplaceAbleDollyResponse queryUnifiedReplacement(ReplaceAbleDto request) {
		ResponseVo<ReplaceAbleDollyResponse> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(ProductWebServiceNameConstants.ONE_KEY_REPLACE, request,
					new TypeReference<ResponseVo<ReplaceAbleDollyResponse>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}
}
