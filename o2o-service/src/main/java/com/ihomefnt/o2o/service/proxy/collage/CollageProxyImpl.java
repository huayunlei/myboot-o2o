package com.ihomefnt.o2o.service.proxy.collage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.collage.dto.CancelCollageOrderParam;
import com.ihomefnt.o2o.intf.domain.collage.dto.CollageInfoDto;
import com.ihomefnt.o2o.intf.domain.collage.dto.CreateCollageOrderDto;
import com.ihomefnt.o2o.intf.domain.collage.dto.CreateCollageOrderParam;
import com.ihomefnt.o2o.intf.domain.collage.dto.GroupBuyActivityDto;
import com.ihomefnt.o2o.intf.domain.collage.dto.ProductDto;
import com.ihomefnt.o2o.intf.domain.collage.dto.UserInfoDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.OmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.collage.CollageProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;

@Service
public class CollageProxyImpl implements CollageProxy {
	
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;


	@Override
	public GroupBuyActivityDto queryCollageActivityDetail(Integer activityId) {
		Map<String, Object> param = new HashMap<>();
		param.put("activityId", activityId);
		HttpBaseResponse<GroupBuyActivityDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.SERVER_URL_QUERY_ACTIVITY, param,
                new TypeReference<HttpBaseResponse<GroupBuyActivityDto>>() {
                });
		
		if (null != response && response.getCode() == 1 && response.getObj() != null) {
			return response.getObj();
		}
		return null;
	}

	@Override
	public ProductDto queryActivityProduct(Integer activityId) {
		Map<String, Object> param = new HashMap<>();
		param.put("activityId", activityId);
		HttpBaseResponse<List<ProductDto>> response = strongSercviceCaller.post(WcmWebServiceNameConstants.SERVER_URL_QUERY_ACTIVITY_PRODUCT, param,
                new TypeReference<HttpBaseResponse<List<ProductDto>>>() {
                });
		
		if (null != response && response.getCode() == 1 && response.getObj() != null) {
			List<ProductDto> list = response.getObj();
			if (!CollectionUtils.isEmpty(list)) {
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public UserInfoDto queryUserInfoByOpenId(String openid, Integer width) {
		Map<String, Object> param = new HashMap<>();
		param.put("openid", openid);
		param.put("width", width);
		HttpBaseResponse<UserInfoDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.SERVER_URL_COLLAGE_RECORD, param,
                new TypeReference<HttpBaseResponse<UserInfoDto>>() {
                });
		
		if (null != response && response.getCode() == 1 && response.getObj() != null) {
			return response.getObj();
		}
		return null;
	}

	@Override
	public CollageInfoDto queryCollageInfoById(Integer groupId) {
		Map<String, Object> param = new HashMap<>();
		param.put("groupId", groupId);
		HttpBaseResponse<CollageInfoDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.SERVER_URL_COLLAGE, param,
                new TypeReference<HttpBaseResponse<CollageInfoDto>>() {
                });
		
		if (null != response && response.getCode() == 1 && response.getObj() != null) {
			return response.getObj();
		}
		return null;
	}

	@Override
	public CreateCollageOrderDto createCollageOrder(CreateCollageOrderParam orderParam) {
		HttpBaseResponse<CreateCollageOrderDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.SERVER_URL_CREATE_COLLAGE_ORDER, orderParam,
                new TypeReference<HttpBaseResponse<CreateCollageOrderDto>>() {
                });
		
		if (null != response && response.getCode() == 1 && response.getObj() != null) {
			return response.getObj();
		}
		return null;
	}

	@Override
	public boolean cancelCollageOrder(CancelCollageOrderParam cancelCollageOrderParam) {
		ResponseVo<?> response = strongSercviceCaller.post(OmsWebServiceNameConstants.SERVER_URL_CANCEL_COLLAGE_ORDER, cancelCollageOrderParam, ResponseVo.class);
		
		if (null != response && response.isSuccess()) {
			return true;
		}
		return false;
	}

}
