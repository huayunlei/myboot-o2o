package com.ihomefnt.o2o.intf.domain.order.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrder;
import lombok.Data;

import java.util.List;

/**
 * Created by shirely_geng on 15-1-22.
 */

/**
 * 套装产品整套预订
 */
@Data
public class HttpSubmitCompositeOrderRequest extends HttpBaseRequest {
    private String mobile;
    private String user;
    private List<ProductOrder> orderList;
    private int isUseCoupon;//是否使用现金券
    
    private Long compositeProductId;//套装的产品ID
}
