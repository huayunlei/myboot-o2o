package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.ProductInfomationResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.SearchResult;
import com.ihomefnt.o2o.intf.domain.product.doo.UserComment;
import lombok.Data;

/**
 * Created by piweiwen on 15-1-20.
 */
@Data
public class HttpProductMoreInformationRsponse{
	
	private ProductInfomationResponse productInfomation;
	private int collection;
	private int shoppingCartCnt;//购物车商品数量,-1表示未登录,大于等于0表示accessToken有效
	private int shoppingCart;//0表示未加入购物车,1表示已加入购物车
	private List<SearchResult> suitList;//所在套装
	private List<UserComment> userCommentList;//单品评论（最多一条）
	private int consultCount;//咨询数
	private int commentCount;//评论数
	private String downloadUrl;//下载链接
	
}
