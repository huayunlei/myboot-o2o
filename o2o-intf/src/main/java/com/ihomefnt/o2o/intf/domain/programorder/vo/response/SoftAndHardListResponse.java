package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderHardItem;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderSoftItem;
import lombok.Data;

/**
 * 软硬装清单
 * @author ZHAO
 */
@Data
public class SoftAndHardListResponse {
	private OrderSoftItem orderSoftItem;//软装清单
	
	private OrderHardItem orderHardItem;//硬装清单
	
	private String homeAdviserMobile;//置家顾问电话
	
	private String activeGift;//活动赠品。已逗号分隔表示换行
	
	private boolean confirmationFlag;//需求确认标志
	
	private String confirmationTime;//需求确认时间
	
	private String scheduledDate;//预计开工时间

	public SoftAndHardListResponse() {
		this.orderSoftItem = new OrderSoftItem();
		this.orderHardItem = new OrderHardItem();
		this.homeAdviserMobile = "";
		this.activeGift = "";
		this.confirmationFlag = false;
		this.confirmationTime = "";
		this.scheduledDate = "";
	}
}
