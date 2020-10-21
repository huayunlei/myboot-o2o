package com.ihomefnt.o2o.intf.domain.order.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class HttpUserOrderRequest extends HttpBaseRequest{
	private Long orderId;
	private Long reasonId;
}
