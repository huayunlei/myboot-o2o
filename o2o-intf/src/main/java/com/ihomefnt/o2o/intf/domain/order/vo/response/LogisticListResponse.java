package com.ihomefnt.o2o.intf.domain.order.vo.response;

import com.ihomefnt.o2o.intf.domain.ordersnapshot.dto.OrderSnapshotProductResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author ZHAO
 */
@Data
@ApiModel("所有物流信息列表返回数据")
public class LogisticListResponse {
	private Integer orderId;// 订单id
	
	private List<LogisticResponse> logisticList;// 订单关联的快递信息

	private Integer productCount;//商品总件数

	private Integer filledCount;//已发货件数

	private Integer unfilledCount;//未发货件数
	
	@ApiModelProperty(value = "商品map", hidden = true)
	private Map<Integer, OrderSnapshotProductResponse> orderProductMap;
}
