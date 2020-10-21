package com.ihomefnt.o2o.intf.domain.order.dto;


import lombok.Data;

/**
 * Created by shirely_geng on 15-1-23.
 */
@Data
public class TOrderDetail {
    private Long orderDetailsId;
    private Long orderId;
    private Long productId;
    private Long productAmount;
    private Double productPrice;
    private Double productPriceAmount;
}
