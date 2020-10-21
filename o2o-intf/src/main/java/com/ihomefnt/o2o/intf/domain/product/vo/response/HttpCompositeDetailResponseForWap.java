package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.CompositeProductReponse;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponseForWap;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-22.
 */
@Data
public class HttpCompositeDetailResponseForWap {
	private CompositeProductReponse compositeProduct;
	private List<ProductSummaryResponseForWap> singleList;
}
