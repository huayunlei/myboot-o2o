package com.ihomefnt.o2o.intf.domain.user.vo.response;

import com.ihomefnt.o2o.intf.domain.product.doo.UserInspirationFavorites;
import com.ihomefnt.o2o.intf.domain.product.doo.UserProductFavoritesResponse;
import lombok.Data;

import java.util.List;

/**
 * Created by piweiwen on 15-1-20.
 */
@Data
public class UserFavoritesResponseVo{

	private List<UserProductFavoritesResponse> userProductFavorites;
	private List<UserInspirationFavorites> userInspirationFavorites;
	
	private int totalRecords;
    private int totalPages;
}
