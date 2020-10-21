package com.ihomefnt.o2o.intf.domain.cart.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrder;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartAddRequestVo extends HttpBaseRequest{
	private List<ProductOrder> productList;

}
