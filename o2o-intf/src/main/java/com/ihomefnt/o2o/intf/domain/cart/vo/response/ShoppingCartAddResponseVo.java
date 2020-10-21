package com.ihomefnt.o2o.intf.domain.cart.vo.response;

import lombok.Data;

@Data
public class ShoppingCartAddResponseVo {

	private int shoppingCartCnt;//购物车商品数量,-1表示未登录,大于等于0表示accessToken有效
}
