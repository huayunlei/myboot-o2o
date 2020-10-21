package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

import java.util.List;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
public class ProductSummaryResponseForWap {
	private String firstContentsName;
	private List<String> secondContentsName;
	private Long count;
	private List<ProductSummaryResponse> content;
}
