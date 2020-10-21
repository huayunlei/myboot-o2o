package com.ihomefnt.o2o.intf.domain.product.vo.response;

import com.ihomefnt.o2o.intf.domain.common.http.PageModel;
import com.ihomefnt.o2o.intf.domain.product.doo.TreeNode;
import lombok.Data;

import java.util.List;

@Data
public class HttpSuitResponse {

    private PageModel dataModel;
    
    private List<TreeNode> classifyTreeList;
    
	private int shoppingCartCnt;//购物车商品数量,-1表示未登录,大于等于0表示accessToken有效
	
	private String seo_title; //seo文章标题
    
    private String seo_keyword; //seo文章key
    
    private String seo_description; //seo文章描述
    
    private String roomName; //空间名称
}
