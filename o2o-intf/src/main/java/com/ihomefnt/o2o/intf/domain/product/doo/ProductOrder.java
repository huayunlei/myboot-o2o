package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

/**
 * Created by shirely_geng on 15-1-24.
 */
@Data
public class ProductOrder {

    private Long productId;
    private String productName;//商品名称
    private Long amount;

}
