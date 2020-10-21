package com.ihomefnt.o2o.intf.proxy.collage;

import com.ihomefnt.o2o.intf.domain.collage.dto.CancelCollageOrderParam;
import com.ihomefnt.o2o.intf.domain.collage.dto.CollageInfoDto;
import com.ihomefnt.o2o.intf.domain.collage.dto.CreateCollageOrderDto;
import com.ihomefnt.o2o.intf.domain.collage.dto.CreateCollageOrderParam;
import com.ihomefnt.o2o.intf.domain.collage.dto.GroupBuyActivityDto;
import com.ihomefnt.o2o.intf.domain.collage.dto.ProductDto;
import com.ihomefnt.o2o.intf.domain.collage.dto.UserInfoDto;

public interface CollageProxy {
	
	GroupBuyActivityDto queryCollageActivityDetail(Integer activityId);
    
	ProductDto queryActivityProduct(Integer activityId);
    
	UserInfoDto queryUserInfoByOpenId(String openid, Integer width);

	CollageInfoDto queryCollageInfoById(Integer groupId);

	CreateCollageOrderDto createCollageOrder(CreateCollageOrderParam orderParam);

	boolean cancelCollageOrder(CancelCollageOrderParam cancelCollageOrderParam);



}
