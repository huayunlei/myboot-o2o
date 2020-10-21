package com.ihomefnt.o2o.intf.domain.cart.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartListRequestVo extends HttpBaseRequest{

	private Long productId;
	
	private List<Long> productIds;
}
