package com.ihomefnt.o2o.intf.domain.product.doo;


/**
 * Created by shirely_geng on 15-1-26.
 */

import lombok.Data;

/**
 * 用于用户下订单的时候，获取套装中包含的单品的信息，包括产品ID，当前价格，产品的数量
 */
@Data
public class CompositeSingleRelation {
    private Long productId;
    private Long productCount;
    private Double priceCurrent;

}
