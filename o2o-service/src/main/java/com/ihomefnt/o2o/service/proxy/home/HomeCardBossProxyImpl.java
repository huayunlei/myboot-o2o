package com.ihomefnt.o2o.service.proxy.home;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpReturnCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.AppHousePropertyResultDto;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.ProjectResponse;
import com.ihomefnt.o2o.intf.domain.homebuild.dto.SpecificUserDecisionResultDto;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNABaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNAInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.DNARoomAndItemVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.SolutionInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.homecard.dto.ApartmentInfoVo;
import com.ihomefnt.o2o.intf.domain.homecard.vo.response.ApartmentRoomVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.BasePropertyResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.AladdinOrderServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.DollyWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.HtpHouseServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.proxy.home.HomeCardBossProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * APP3.0新版首页产品中心服务代理DAO层实现层
 * 
 * @author ZHAO
 */
@Service
public class HomeCardBossProxyImpl implements HomeCardBossProxy {
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public SolutionInfoResponseVo getUserSpecificProgram(Integer userId, Integer houseProjectId, Integer houseTypeId,
														 Integer queryResultCount) {
		JSONObject param = new JSONObject();
		param.put("userId", userId);
		param.put("houseProjectId", houseProjectId);
		param.put("houseTypeId", houseTypeId);
		param.put("queryResultCount", queryResultCount);
		ResponseVo<SolutionInfoResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_PREPARED_SOLUTION_LIST_WITH_USER_INFO, param,
					new TypeReference<ResponseVo<SolutionInfoResponseVo>>() {});
		} catch (Exception e) {
			return null;
		}

		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public List<BasePropertyResponseVo> getProductFilterInfo() {
		JSONObject param = new JSONObject();
		ResponseVo<List<BasePropertyResponseVo>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_BASE_PROPERTIES, param,
					new TypeReference<ResponseVo<List<BasePropertyResponseVo>>>() {});
		} catch (Exception e) {
			return null;
		}

		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public DNABaseInfoResponseVo getProductByCondition(Integer userId, Integer style, List<Integer> styleIdList, Integer roomUseId, Integer seriesId,
                                                       Integer pageNum, Integer pageSize) {
		JSONObject param = new JSONObject();
		if (userId != null && userId > 0) {
			param.put("artistId", userId);
		}
		if (seriesId != null && seriesId > 0) {
			param.put("seriesId", seriesId);
		}
		if (style != null && style > 0) {
			param.put("styleId", style);
		}
		if (styleIdList != null && !styleIdList.isEmpty()) {
			param.put("styleIds", styleIdList);
		}
		if (roomUseId != null &&roomUseId > 0) {
			param.put("roomUseId", roomUseId);
		}
		if (pageNum== null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize== null||pageSize == 0) {
			pageSize = 10;
		}
		param.put("pageNum", pageNum);
		param.put("pageSize", pageSize);
		ResponseVo<DNABaseInfoResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post("dolly-web.dna-app.queryDNABaseInfoWithParams", param,
					new TypeReference<ResponseVo<DNABaseInfoResponseVo>>() {});
		} catch (Exception e) {
			return null;
		}

		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public DNAInfoResponseVo getDnaDetailById(Integer dnaId) {
		JSONObject param = new JSONObject();
		param.put("dnaId", dnaId);
		ResponseVo<?> responseVo = AppRedisUtil.getInterfaceRedis("dolly-web.dna-app.queryDNADetailWithId",String.valueOf(dnaId));
		try{
			if(responseVo == null){
				responseVo = strongSercviceCaller.post("dolly-web.dna-app.queryDNADetailWithId", param,
						ResponseVo.class);
				AppRedisUtil.setInterfaceRedis("dolly-web.dna-app.queryDNADetailWithId",String.valueOf(dnaId), responseVo);
			}
		} catch (Exception e) {
			return null;
		}

		DNAInfoResponseVo vo = new DNAInfoResponseVo();
		if (responseVo.isSuccess()) {
			if (responseVo.getData() != null) {
				vo = JsonUtils.json2obj(JsonUtils.obj2json(responseVo.getData()), DNAInfoResponseVo.class);
			}
		}
		return vo;
	}

	@Override
	public List<DNARoomAndItemVo> getSoftListByCondition(Integer dnaId) {
		JSONObject param = new JSONObject();
		param.put("dnaId", dnaId);
		ResponseVo<List<DNARoomAndItemVo>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post("dolly-web.dna-app.queryRoomItemListWithDNAid", param,
					new TypeReference<ResponseVo<List<DNARoomAndItemVo>>>() {});
		} catch (Exception e) {
			return null;
		}

		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public List<ApartmentRoomVo> getHouseInfoByLayoutId(Integer layoutId) {
		ResponseVo<List<ApartmentRoomVo>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_ROOM_DETAIL_LIST_WITH_HOUSEID, layoutId,
					new TypeReference<ResponseVo<List<ApartmentRoomVo>>>() {});
		} catch (Exception e) {
			return null;
		}

		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	/**
	 * 户型点选
	 * @param map
	 * @return
	 */
	@Override
	public ApartmentInfoVo getHouseInfoByApartmentId(Map map) {
		ResponseVo<ApartmentInfoVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_ROOM_DETAIL_LIST_BY_HOUSE_ID, map,
					new TypeReference<ResponseVo<ApartmentInfoVo>>() {});
		} catch (Exception e) {
			throw new BusinessException(HttpReturnCode.DOLLY_FAILED, MessageConstant.FAILED);
		}
		if(responseVo == null){
			throw new BusinessException(HttpReturnCode.DOLLY_FAILED, MessageConstant.FAILED);
		}
		if(!responseVo.isSuccess()){
			throw new BusinessException(HttpReturnCode.DOLLY_FAILED, MessageConstant.FAILED);
		}
		return responseVo.getData();
	}


	/**
	 * 户型点选（新）调用中台
	 * @param map
	 * @return
	 */
	@Override
	public ApartmentInfoVo getHouseInfoByApartmentIdNew(Map map) {
		ResponseVo<ApartmentInfoVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(HtpHouseServiceNameConstants.QUERY_ROOM_DETAIL_LIST_BY_HOUSE_ID, map,
					new TypeReference<ResponseVo<ApartmentInfoVo>>() {});
		} catch (Exception e) {
			throw new BusinessException(HttpReturnCode.FTP_FAILED, MessageConstant.FAILED);
		}
		if(responseVo == null){
			throw new BusinessException(HttpReturnCode.FTP_FAILED, MessageConstant.FAILED);
		}
		if(!responseVo.isSuccess()){
			throw new BusinessException(HttpReturnCode.FTP_FAILED, MessageConstant.FAILED);
		}
		return responseVo.getData();
	}

	@Override
    public ProjectResponse queryBuildingDetail(Integer buildingId) {
		ResponseVo<ProjectResponse> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(DollyWebServiceNameConstants.QUERY_BUILDING_DETAIL, buildingId,
					new TypeReference<ResponseVo<ProjectResponse>>() {});
		} catch (Exception e) {
			return null;
		}

		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
    }

	@Override
	public SpecificUserDecisionResultDto queryUserHouseSpecific(Integer userId, Set<Integer> houseIdSet) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("houseIds", houseIdSet);

		ResponseVo<SpecificUserDecisionResultDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.CHECK_SPECIFIC_USER, paramMap,
					new TypeReference<ResponseVo<SpecificUserDecisionResultDto>>() {});
		} catch (Exception e) {
			return null;
		}

		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

	@Override
	public AppHousePropertyResultDto queryHouseDetail(Integer customerHouseId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("customerHouseId", customerHouseId);

		ResponseVo<AppHousePropertyResultDto> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(AladdinOrderServiceNameConstants.QUERY_HOUSE_INFO_BY_HOUSE_ID, paramMap,
					new TypeReference<ResponseVo<AppHousePropertyResultDto>>() {});
		} catch (Exception e) {
			return null;
		}

		if (responseVo != null && responseVo.isSuccess() && responseVo.getData() != null) {
			return responseVo.getData();
		}
		return null;
	}

}
