package com.ihomefnt.o2o.intf.domain.order.vo.response;

import com.ihomefnt.o2o.intf.domain.kuaidi100.vo.response.KuaidiProductDeliveryResponseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 
 * @author ZHAO
 */
@Data
@ApiModel("所有物流信息返回数据")
public class LogisticResponse {
	private Integer orderId; //订单ID

	private List<OrderDetailResp> orderDetailRespList; // 商品信息

	private String logisticNum; // 物流单号

	private String logisticCompanyName; // 物流公司名称

	private String logisticCompanyCode; // 物流公司编码

	private List<KuaidiProductDeliveryResponseVo> data; // 运输记录

	/* 快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态 */
	private Integer state;
	
	private String stateDesc;//中文名称
	
	private Integer goodsCount;//物流所含商品数
}
