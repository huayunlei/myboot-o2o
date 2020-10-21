package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.SearchResult;
import lombok.Data;

/**
 * Created by wangxiao on 15-12-17.
 */
@Data
public class HttpRecommendListResponse {
    
	private List<SearchResult> sameHouse;
	
	private List<SearchResult> guessYouLike;
}
