package com.ihomefnt.o2o.intf.service.popup;

import com.ihomefnt.o2o.intf.domain.popup.vo.response.PopupResponseVo;
import com.ihomefnt.o2o.intf.domain.right.vo.request.OrderRightPopupRequest;

/**
 * @author huayunlei
 * @created 2018年12月6日 下午4:59:21
 * @desc 
 */
public interface PopupService {

	/**判断权益弹框
	 * @param request
	 * @return
	 */
	PopupResponseVo judgeRightPopup(OrderRightPopupRequest request);

	/**取消权益弹框
	 * @param request
	 */
	void cancelRightPopup(OrderRightPopupRequest request);

}
