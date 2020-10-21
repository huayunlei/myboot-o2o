package com.ihomefnt.o2o.intf.domain.user.vo.response;

import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpPictureListResponse;
import lombok.Data;

/**
 * Created by shirely_geng on 15-1-18.
 */
@Data
public class UserFavoritesAllResponseVo {
	UserFavoritesResponseVo singleFavoritesResponse;
	UserFavoritesResponseVo roomFavoritesResponse;
	UserFavoritesResponseVo suitFavoritesResponse;
	int productCount;
	
	UserFavoritesResponseVo caseFavoritesResponse;
	UserFavoritesResponseVo strategyFavoritesResponse;
	HttpPictureListResponse alumFavoritesResponse;
	int inspirationCount;
}
