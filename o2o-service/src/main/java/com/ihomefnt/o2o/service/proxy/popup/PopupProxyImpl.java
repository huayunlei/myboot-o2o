package com.ihomefnt.o2o.service.proxy.popup;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.domain.popup.dto.PopupRuleDto;
import com.ihomefnt.o2o.intf.domain.popup.vo.request.PopupRuleRequestVo;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.popup.PopupProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;

@Service
public class PopupProxyImpl implements PopupProxy {
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;
	
	@Override
	public PopupRuleDto queryPopupRuleByParams(PopupRuleRequestVo popupRuleRequest) {
		HttpBaseResponse<PopupRuleDto> responseVo = null;
		try{
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_POPUP_RULE_BY_PARAMS, popupRuleRequest, 
					new TypeReference<HttpBaseResponse<PopupRuleDto>>() {
					});
		}catch(Exception e){
			return null;
		}
		if(null != responseVo && responseVo.getObj() != null){
			return responseVo.getObj();
		}
		return null;
	}

}
