package com.ihomefnt.o2o.service.proxy.program;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.proxy.program.KeywordWcmProxy;
import com.ihomefnt.o2o.intf.domain.program.dto.KeywordListResponseVo;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * WCM服务代理DAO层实现层
 * 
 * @author ZHAO
 */
@Service
public class KeywordWcmProxyImpl implements KeywordWcmProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public KeywordListResponseVo getKeywordList(List<String> keywordList) {
		JSONObject param = new JSONObject();
		param.put("keywordList", keywordList);
		HttpBaseResponse<KeywordListResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_KEYWORD_LIST, param,
					new TypeReference<HttpBaseResponse<KeywordListResponseVo>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (null != responseVo && responseVo.getObj() != null) {
			return responseVo.getObj();
		}
		return null;
	}

}
