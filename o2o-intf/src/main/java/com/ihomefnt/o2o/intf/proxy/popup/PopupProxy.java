package com.ihomefnt.o2o.intf.proxy.popup;

import com.ihomefnt.o2o.intf.domain.popup.dto.PopupRuleDto;
import com.ihomefnt.o2o.intf.domain.popup.vo.request.PopupRuleRequestVo;

public interface PopupProxy {

	PopupRuleDto queryPopupRuleByParams(PopupRuleRequestVo popupRuleRequest);

}
