package com.ihomefnt.o2o.intf.domain.user.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by piweiwen on 15-1-20.
 */
@Data
public class UserAddWishListRequestVo extends HttpBaseRequest {

	private String wishProductName;
	private String wishProductBrand;
	private String wishProductUrl;
	private String wishProductRequest;
}
