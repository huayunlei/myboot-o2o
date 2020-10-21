package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.UserOrderResponse120;
import lombok.Data;

@Data
public class HttpUserOrderListResponse120 {
	private List<UserOrderResponse120> userOrderList;
}
