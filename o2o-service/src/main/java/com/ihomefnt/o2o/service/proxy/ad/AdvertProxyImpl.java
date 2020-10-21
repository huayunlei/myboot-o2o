package com.ihomefnt.o2o.service.proxy.ad;

import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertListDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.proxy.ad.AdvertProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 启动页
 * @author ZHAO
 */
@Service
public class AdvertProxyImpl implements AdvertProxy{
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public AdvertListDto queryStartPageList(Map<String, Object> paramMap) {
		try {
			HttpBaseResponse response = strongSercviceCaller.post(WcmWebServiceNameConstants.ADVERT_QUERY_START_PAGE_LIST, paramMap, HttpBaseResponse.class);

			if (null != response && response.getObj() != null) {
				return JsonUtils.json2obj(JsonUtils.obj2json(response.getObj()), AdvertListDto.class);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}
