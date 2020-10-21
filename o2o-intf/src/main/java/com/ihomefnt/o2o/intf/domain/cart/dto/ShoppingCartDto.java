package com.ihomefnt.o2o.intf.domain.cart.dto;

import lombok.Data;

@Data
public class ShoppingCartDto {

	private Long productId;
	private Long userId;
	private Long amount;
}
