package com.ihomefnt.o2o.service.proxy.dic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicProxyImpl implements DicProxy {
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public DicListDto getDicListByKey(String keyDesc) {
		JSONObject param = new JSONObject();
        param.put("keyDesc", keyDesc);
		try {
			HttpBaseResponse<DicListDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_DIC_LIST_BY_PID_KEY, param,
					new TypeReference<HttpBaseResponse<DicListDto>>() {
			});
			
			if (null != response && response.getObj() != null) {
				return response.getObj();
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}

	@Override
	public DicDto queryDicByKey(String keyDesc) {
		JSONObject param = new JSONObject();
		param.put("keyDesc", keyDesc);
		String redisKey = "o2oQueryDicByKey:" + keyDesc;
		try {
			String result = AppRedisUtil.get(redisKey);
			if (!StringUtil.isNullOrEmpty(result)) {
				return JSON.parseObject(result, DicDto.class);
			} else {
				HttpBaseResponse<DicDto> response = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_DIC_BY_KEY, param,
						new TypeReference<HttpBaseResponse<DicDto>>() {
				});

				if (null != response && response.getObj() != null) {
					AppRedisUtil.set(redisKey, JSON.toJSONString(response.getObj()), 7 * 24 * 3600);
					return response.getObj();
				}
			}

		} catch (Exception e) {
			return null;
		}
		
		return null;
	}


}
