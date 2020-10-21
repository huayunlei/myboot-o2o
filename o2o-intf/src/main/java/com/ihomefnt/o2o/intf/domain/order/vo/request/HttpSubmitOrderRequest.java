package com.ihomefnt.o2o.intf.domain.order.vo.request;

import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrder;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-23.
 */
@Data
public class HttpSubmitOrderRequest extends HttpBaseRequest {
    private String mobile;
    private String user;
    private ProductOrder productOrder;
    private int isUseCoupon;//是否使用现金券
}
