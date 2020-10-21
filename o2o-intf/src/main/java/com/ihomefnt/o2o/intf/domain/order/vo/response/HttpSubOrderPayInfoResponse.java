package com.ihomefnt.o2o.intf.domain.order.vo.response;

import com.ihomefnt.o2o.intf.domain.order.dto.OrderPayRecord;
import lombok.Data;

import java.util.List;

@Data
public class HttpSubOrderPayInfoResponse {
	private String orderNum;//订单编号
    private List<OrderPayRecord> orderPayRecord;//支付明细列表
    private int totalRecords;//记录总数
}
