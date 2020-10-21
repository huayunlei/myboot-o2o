package com.ihomefnt.o2o.intf.domain.promotion.vo.response;

import com.ihomefnt.o2o.intf.domain.programorder.dto.HomeCarnivalInfo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.HomeCarnivalOrderInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HomeCarnivalInfoResponse {

	private List<HomeCarnivalOrderInfo> allProductOrderList;//全品家订单信息
	
	private HomeCarnivalInfo homeCarnivalInfo;//1219置家狂欢节信息

	public HomeCarnivalInfoResponse() {
		this.allProductOrderList = new ArrayList<>();
		this.homeCarnivalInfo = new HomeCarnivalInfo();
	}
}
