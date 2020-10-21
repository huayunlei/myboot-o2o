package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import lombok.Data;

/**
 * Created by piweiwen on 15-1-19.
 */
@Data
public class HttpProductMoreSingleResponse {
	
    private List<ProductSummaryResponse> singleList;
    
    private Long totalRecords; 
    
    private int totalPages;
    
}
