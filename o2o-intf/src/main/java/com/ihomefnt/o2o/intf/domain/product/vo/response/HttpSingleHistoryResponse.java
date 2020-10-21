package com.ihomefnt.o2o.intf.domain.product.vo.response;

import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import lombok.Data;

import java.util.List;

@Data
public class HttpSingleHistoryResponse {

	private List<ProductSummaryResponse> singleList;

}
