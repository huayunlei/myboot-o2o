package com.ihomefnt.o2o.intf.domain.product.vo.response;

import com.ihomefnt.o2o.intf.domain.configItem.vo.response.HttpItemResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.UserOrderResponse120;
import lombok.Data;

import java.util.List;

@Data
public class HttpUserOrderResponse120 {
	private List<UserOrderResponse120> userOrderList;
	private List<HttpItemResponse> itemList;
}
