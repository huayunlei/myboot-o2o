package com.ihomefnt.o2o.intf.service.user;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpPictureListResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.ProductOrderDetail;
import com.ihomefnt.o2o.intf.domain.product.doo.TWishList;
import com.ihomefnt.o2o.intf.domain.product.doo.UserOrder;
import com.ihomefnt.o2o.intf.domain.product.doo.UserOrderResponse120;
import com.ihomefnt.o2o.intf.domain.user.vo.request.AjbInfoRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.MyAccountRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserAddWishListRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserFavoritesRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserOrderRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.request.UserWishListRequestVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AccountInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.AjbInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.MyConfigResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserAddWishListResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserFavoritesAllResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserFavoritesResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserOrderListResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.UserWishListResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.WishListResponseVo;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.order.vo.request.HttpUserOrderRequest;

/** 
* @ClassName: MySetService 
* @Description: 我的设置
* @author huayunlei
* @date Feb 20, 2019 5:50:11 PM 
*  
*/
public interface MyService {

	/** 
	* @Title: getAllFavorites 
	* @Description: 我的所有收藏2.0以前
	* @param @param request
	* @return HttpUserFavoritesResponse    返回类型 
	* @throws 
	*/
	UserFavoritesResponseVo getAllFavorites(UserFavoritesRequestVo request);

	/** 
	* @Title: getAllFavorites200 
	* @Description: 我的所有收藏2.0以后 
	* @param @param request
	* @param @return  参数说明 
	* @return UserFavoritesResponse    返回类型 
	* @throws 
	*/
	UserFavoritesAllResponseVo getAllFavorites200(UserFavoritesRequestVo request);

	UserFavoritesResponseVo moreUserFavorites(UserFavoritesRequestVo request, Long userId);

	HttpPictureListResponse morePictureAlbumList(UserFavoritesRequestVo request, Long userId);

	UserWishListResponseVo getAllWishList(UserWishListRequestVo request);

	/** 
	* @Title: queryAllWishList 
	* @Description: 我的侃价
	* @param @param userId
	* @return List<WishListResponse>    返回类型 
	* @throws 
	*/
	List<WishListResponseVo> queryAllWishList(Long userId);

	/** 
	* @Title: addWishList 
	* @Description: 获取用户的侃价信息 
	* @param @param request
	* @return HttpUserAddWishListResponse    返回类型 
	* @throws 
	*/
	UserAddWishListResponseVo addWishList(UserAddWishListRequestVo request);

	/** 
	* @Title: getAllOrder 
	* @Description: 查询用户的订单信息
	* @param @param request
	* @return HttpUserOrderResponse    返回类型 
	* @throws 
	*/
	UserOrderListResponseVo getAllOrder(UserOrderRequestVo request);

	MyConfigResponseVo configByOrderId(UserOrderRequestVo request);

	MyConfigResponseVo config(HttpBaseRequest request);

	AjbInfoResponseVo getAjbInfo(AjbInfoRequestVo request);

	/** 
	* @Title: getAccountInfo 
	* @Description: 获取我的账户信息 
	* @param @param request
	* @return AccountInfoResponseVo    返回类型 
	* @throws 
	*/
	AccountInfoResponseVo getAccountInfo(MyAccountRequestVo request);

	Long addWishList(TWishList wishList);

	List<UserOrderResponse120> queryAllUserOrder120(Long userId, Long orderId);

	UserOrder queryUserOrderByOrderId(Long orderId);

	Boolean cancelOrder(HttpUserOrderRequest userOrderRequest);

	Boolean deleteOrder(HttpUserOrderRequest userOrderRequest);

	List<ProductOrderDetail> queryOrderDetailsByOrderId(Long orderId);
}
