package com.ihomefnt.o2o.intf.dao.product;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.product.doo.TWishList;

/**
 * Created by piweiwen on 15-1-21.
 */
public interface WishListDao {

	/**
	 * 我的侃价
	 */
	List<TWishList> queryAllWishList(Long userId);

	/**
	 * 添加侃价
	 */
	Long addWishList(TWishList wishList);
}
