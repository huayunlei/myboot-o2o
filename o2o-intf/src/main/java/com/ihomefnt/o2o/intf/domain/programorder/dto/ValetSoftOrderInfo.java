package com.ihomefnt.o2o.intf.domain.programorder.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 代客下单 软装订单
 * @author ZHAO
 */
@Data
public class ValetSoftOrderInfo {
	private Integer softOrderId;// 软装订单id
	
	private Integer softOrderStatus;// 软装订单状态
	
	private String softOrderStatusStr;// 软装订单状态字符串
	
	private String softorderStatusPraise;//软装订单状态文案
	
	private List<ValetSoftProductInfo> valetSoftProductInfoList;//商品集合
	
	private Integer productTotalCount;//商品总数

	public ValetSoftOrderInfo() {
		this.softOrderId = -1;
		this.softOrderStatus = -1;
		this.softOrderStatusStr = "";
		this.softorderStatusPraise = "";
		this.valetSoftProductInfoList = new ArrayList<>();
		this.productTotalCount = 0;
	}
}
