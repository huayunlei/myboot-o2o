package com.ihomefnt.o2o.intf.domain.product.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.Room;
import com.ihomefnt.o2o.intf.domain.product.doo.UserComment;
import lombok.Data;

/**
 * Created by wangxiao on 15-1-20.
 */
@Data
public class HttpRoomDetailsResponse{
	
	private Room roomDetails;
	private int collection;
	private int shoppingCartCnt;//购物车商品数量,-1表示未登录,大于等于0表示accessToken有效
	private int shoppingCart;//0表示未加入购物车,1表示已加入购物车
	private List<UserComment> userCommentList;//用户评论（最多一条）
}
