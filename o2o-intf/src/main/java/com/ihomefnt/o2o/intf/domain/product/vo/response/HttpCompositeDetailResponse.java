package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProductReponse;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-22.
 */
@Data
public class HttpCompositeDetailResponse {
	private CompositeProductReponse compositeProduct;
	private List<ProductSummaryResponse> singleList;
}
