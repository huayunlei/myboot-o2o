package com.ihomefnt.o2o.service.proxy.program;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.proxy.program.HardStandardWcmProxy;
import com.ihomefnt.o2o.intf.domain.program.dto.HardStandardGroupListResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.HardStandardListResponseVo;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 硬装标准WCM服务代理DAO层实现层
 * 
 * @author ZHAO
 */
@Service
public class HardStandardWcmProxyImpl implements HardStandardWcmProxy {
	@Resource
	private StrongSercviceCaller strongSercviceCaller;

	@Override
	public HardStandardListResponseVo queryHardStandByCondition(List<String> seriesNameList) {
		JSONObject param = new JSONObject();
		if(CollectionUtils.isNotEmpty(seriesNameList)){
			param.put("seriesNameList", seriesNameList);
		}
		HttpBaseResponse<HardStandardListResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post("wcm-web.hard.queryHardStandBySeriesName", param,
					new TypeReference<HttpBaseResponse<HardStandardListResponseVo>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (null != responseVo && responseVo.getObj() != null) {
			return responseVo.getObj();
		}
		return null;
	}

	@Override
	public HardStandardGroupListResponseVo queryHardStandGroup() {
		JSONObject param = new JSONObject();
		HttpBaseResponse<HardStandardGroupListResponseVo> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post("wcm-web.hard.queryHardStandGroup", param,
					new TypeReference<HttpBaseResponse<HardStandardGroupListResponseVo>>() {
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
