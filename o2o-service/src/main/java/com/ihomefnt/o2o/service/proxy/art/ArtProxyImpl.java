/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年7月7日
 * Description:ArtProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.art;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.*;
import com.ihomefnt.o2o.intf.domain.art.vo.request.ArtworkOrderRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpArtListRequest;
import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpArtListWithParamRequest;
import com.ihomefnt.o2o.intf.manager.constant.proxy.CmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.IhomeApiServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.OmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.art.ArtProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @see http://192.168.1.12:10014/cms-web/swagger/
 */
@Service
public class ArtProxyImpl implements ArtProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public List<ArtSpace> getArtSpaceList() {
		ResponseVo<List<ArtSpace>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_SPACE_LIST, null, 
					new TypeReference<ResponseVo<List<ArtSpace>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public List<Artwork> getArtworkList(HttpArtListRequest request) {
		if (request == null) {
			return null;
		}

		ResponseVo<List<Artwork>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_LIST, request, 
					new TypeReference<ResponseVo<List<Artwork>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}

	@Override
	public Integer artworkLog(int logId, long productId, long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("logId", logId);
		params.put("productId", productId);
		params.put("userId", userId);

		ResponseVo<Integer> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.ART_WORK_LOG, params, 
					new TypeReference<ResponseVo<Integer>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
    @Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public List<ArtworkImage> getArtworkImages(Long artworkId) {
		if (artworkId == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artworkId", artworkId);

		ResponseVo<List<ArtworkImage>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_IMAGES, params, 
					new TypeReference<ResponseVo<List<ArtworkImage>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}

	@Override
    @Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public Long getViewArtworkCount(Long productId, Long artistId, int typeId) {
		if (productId == null ) {
			return 0L;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("artistId", artistId);
		params.put("typeId", typeId);

		ResponseVo<Long> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_VIEW_ART_WORK_COUNT, params, 
					new TypeReference<ResponseVo<Long>>() {
			});
		} catch (Exception e) {
			return 0L;
		}

		if (responseVo == null) {
			return 0L;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return 0L;

	}

	@Override
    //@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public Artist getArtworkArtistInfoById(Long artistId) {
		if (artistId == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artistId", artistId);

		ResponseVo<Artist> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_ARTIST_INFO_BY_ID, params, 
					new TypeReference<ResponseVo<Artist>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}

	@Override
	//@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public Artwork getArtworkById(Long artworkId) {
		if (artworkId == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artworkId", artworkId);

		ResponseVo<Artwork> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_BY_ID, params, 
					new TypeReference<ResponseVo<Artwork>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}

	@Override
	public Long getArtworkListCount(HttpArtListRequest params) {
		if (params == null) {
			return 0L;
		}

		ResponseVo<Long> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_LIST_COUNT, params, 
					new TypeReference<ResponseVo<Long>>() {
			});
		} catch (Exception e) {
			return 0L;
		}

		if (responseVo == null) {
			return 0L;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return 0L;

	}

	@Override
	public List<Artwork> getArtworkByArtistId(long artistId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artistId", artistId);
		
		ResponseVo<List<Artwork>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_BY_ARTIST_ID, params, 
					new TypeReference<ResponseVo<List<Artwork>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}

	@Override
	public List<String> getArtistExperienceById(long artistId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artistId", artistId);
		
		ResponseVo<List<String>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ARTIST_EXPERIENCE_BY_ID, params, 
					new TypeReference<ResponseVo<List<String>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public List<String> getArtistSelfDescById(long artistId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artistId", artistId);
		
		ResponseVo<List<String>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ARTIST_SELF_DESC_BY_ID, params, 
					new TypeReference<ResponseVo<List<String>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public Artwork getArtworkOrderInfo(Long artworkId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artworkId", artworkId);
		
		ResponseVo<Artwork> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(CmsWebServiceNameConstants.SERVER_QUERY_ARTWORK_ORDER_INFO,params,
					new TypeReference<ResponseVo<Artwork>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}


	/**
	 * 新版本艾商城艺术品简单信息
	 * @param request
	 * @return
	 */
	@Override
	public List<Artwork> getArtworkOmsOrderInfo(ArtworkOrderRequest request) {

		ResponseVo<List<Artwork>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(OmsWebServiceNameConstants.QUERY_ART_WORK_ORDER_INFO,request,
					new TypeReference<ResponseVo<List<Artwork>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}


	@Override
    @Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public ArtStudio getArtworkStudioById(Long artistId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artistId", artistId );
		
		ResponseVo<ArtStudio> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_STUDIO_BY_ID, params, 
					new TypeReference<ResponseVo<ArtStudio>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}


	@Override
	public Long getArtWorkCountByParam(HttpArtListWithParamRequest params) {
		ResponseVo<Long> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_COUNT_BY_PARAM, params,  
					new TypeReference<ResponseVo<Long>>() {
			});
		} catch (Exception e) {
			return 0L;
		}

		if (responseVo == null) {
			return 0L;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return 0L;

	}

	@Override
	public List<Artwork> getArtWorkListByParam(HttpArtListWithParamRequest params) {
		if (params == null) {
			return null;
		}
		ResponseVo<List<Artwork>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_LIST_BY_PARAM, params,  
					new TypeReference<ResponseVo<List<Artwork>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}


	@Override
	public List<ArtworkFilterInfo> getArtworkTypeInfo() {
		ResponseVo<List<ArtworkFilterInfo>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_TYPE_INFO, null,  
					new TypeReference<ResponseVo<List<ArtworkFilterInfo>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}


	@Override
	public List<ArtworkFilterInfo> getArtworkRoomInfo() {
		ResponseVo<List<ArtworkFilterInfo>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_ROOM_INFO, null,  
					new TypeReference<ResponseVo<List<ArtworkFilterInfo>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;
	}
	
	@Override
	//@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public List<Artwork> getArtworksByFilters(Map<String, Object> params) {
		if (params == null) {
			return null;
		}
		
		ResponseVo<List<Artwork>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORKS_BY_FILTERS, params,  
					new TypeReference<ResponseVo<List<Artwork>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}


	@Override
	public List<Double> getArtworkPriceList(Map<String, Object> params) {
		if (params == null) {
			return null;
		}
		
		ResponseVo<List<Double>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_PRICE_LIST, params,  
					new TypeReference<ResponseVo<List<Double>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}


	@Override
	//@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public Long getArtworksByFiltersCount(Map<String, Object> params) {
		if (params == null) {
			return 0L;
		}
		
		ResponseVo<Long> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORKS_BY_FILTERS_COUNT, params,  
					new TypeReference<ResponseVo<Long>>() {
			});
		} catch (Exception e) {
			return 0L;
		}

		if (responseVo == null) {
			return 0L;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return 0L;
	}


	@Override
	public List<Artwork> getArtworksRecommend(Map<String, Object> params) {
		if (params == null) {
			return null;
		}
		ResponseVo<List<Artwork>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORKS_RECOMMEND, params,  
					new TypeReference<ResponseVo<List<Artwork>>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}

	@Override
	public Long getViewArtworkTotalCount(List<Long> artIdList, int op) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productIds", artIdList);
		params.put("typeId", op);

		ResponseVo<Long> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_VIEW_ARTWORK_TOTAL_COUNT, params,  
					new TypeReference<ResponseVo<Long>>() {
			});
		} catch (Exception e) {
			return 0L;
		}

		if (responseVo == null) {
			return 0L;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return 0L;

	}

	@Override
	@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public Artwork getArtworkByIdAndType(Long artworkId, Integer artworkType) {
		if (artworkId == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artworkId", artworkId);
		params.put("artworkType", artworkType);

		ResponseVo<Artwork> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(IhomeApiServiceNameConstants.GET_ART_WORK_BY_ID, params,  
					new TypeReference<ResponseVo<Artwork>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return null;

	}

	@Override
	public ArtworkImageDto getSceneImageById(Long artworkId) {
		if (artworkId == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artworkId", artworkId);

		ResponseVo<ArtworkImageDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(CmsWebServiceNameConstants.GET_ART_WORK_EXPERIENCE_IMAGE, params,
					new TypeReference<ResponseVo<ArtworkImageDto>>() {
			});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null) {
			return null;
		}
		if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
		return  null;
	}

}
