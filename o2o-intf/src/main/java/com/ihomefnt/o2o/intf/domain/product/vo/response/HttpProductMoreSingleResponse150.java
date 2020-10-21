package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.ProductSummaryResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.TreeNode;
import lombok.Data;

/**
 * Created by piweiwen on 15-1-19.
 */
@Data
public class HttpProductMoreSingleResponse150 {
	
    private List<ProductSummaryResponse> singleList;
    private List<TreeNode> classifyTreeList;
    private Long totalRecords;

    private int totalPages;
    
    private int shoppingCartCnt;//购物车商品数量,-1表示未登录,大于等于0表示accessToken有效
    
    private String seo_title; 
    
    private String seo_keyword ;
    private String seo_description;
}
