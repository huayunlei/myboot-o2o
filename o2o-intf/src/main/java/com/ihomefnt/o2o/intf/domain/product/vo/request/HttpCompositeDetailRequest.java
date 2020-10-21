package com.ihomefnt.o2o.intf.domain.product.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-22.
 */
@Data
public class HttpCompositeDetailRequest extends HttpBaseRequest {
    private Long compositeProductId;

}
