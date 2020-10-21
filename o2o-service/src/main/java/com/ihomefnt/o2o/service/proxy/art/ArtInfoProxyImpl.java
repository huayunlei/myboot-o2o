package com.ihomefnt.o2o.service.proxy.art;

import com.ihomefnt.o2o.intf.domain.art.dto.ArtListResponseVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.CmsWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.art.ArtInfoProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 艺术品代理
 * @see http://192.168.1.30:10014/cms-web/swagger/
 * @author ZHAO
 */
@Service
public class ArtInfoProxyImpl implements ArtInfoProxy {
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	//@Cacheable(cacheNames="o2o-api",keyGenerator = "springCacheKeyGenerator")
	public ArtListResponseVo queryArtListByFilters(Map<String, Object> params) {
		HttpBaseResponse<ArtListResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(CmsWebServiceNameConstants.QUERY_APP_ART_LIST_BY_FILTER, params,
					new TypeReference<HttpBaseResponse<ArtListResponseVo>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo.getObj() != null) {
			return responseVo.getObj();
		}
		return null;
	}

}
