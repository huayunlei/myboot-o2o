package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.util.List;
@Data
public class UserOrderResponse120 {
	private Long orderId;
    private String name;
    private Double orderPrice;
    private Long productCount;
    private Long orderStatus;
    private List<String> pictureUrlOriginal;
    private String deliveryTime;
    private Double leftMoney;
    private List<String> productImages;
    private Integer actCode;
	public UserOrderResponse120(UserOrder userOrder) {
		this.orderId = userOrder.getOrderId();
        this.name = userOrder.getName();
        this.orderPrice = userOrder.getOrderPrice();
        this.productCount = userOrder.getProductCount();
        this.orderStatus = userOrder.getOrderStatus();
        this.deliveryTime=userOrder.getDeliveryTime();
        this.actCode = userOrder.getActCode();
	}
}
