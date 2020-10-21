package com.ihomefnt.o2o.intf.domain.user.vo.response;

import com.ihomefnt.o2o.intf.domain.product.doo.TWishList;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by piweiwen on 15-1-21.
 */
@Data
@NoArgsConstructor
public class WishListResponseVo {

	/** 主鍵 */
	private Long wishListId;
	private String wishProductName;
	private String wishProductBrand;
	private List<String> wishProductUrl;
	private String wishProductRequest;
	private String submitTime;

	public WishListResponseVo(TWishList wishList) {
		this.wishListId = wishList.getWishListId();
		this.wishProductName = wishList.getWishProductName();
		this.wishProductBrand = wishList.getWishProductBrand();
		this.wishProductRequest = wishList.getWishProductRequest();
		if (null == wishList.getSubmitTime()) {
			this.submitTime = null;
		} else {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.submitTime = sdf.format(wishList.getSubmitTime().clone());
		}
	}
}
