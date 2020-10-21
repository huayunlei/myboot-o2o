package com.ihomefnt.o2o.intf.domain.experiencestore.dto;

import com.ihomefnt.o2o.intf.domain.address.dto.TChannel;
import com.ihomefnt.o2o.intf.domain.address.dto.TCityConfig;
import com.ihomefnt.o2o.intf.domain.product.dto.TProductSummary;
import com.ihomefnt.o2o.intf.domain.suit.dto.ExpStoreSuitDto;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 体验店首页缓存对象
 * 
 * @author Ivan
 * @date 2016年5月10日 下午4:56:04
 */
@Data
public class ExpStoreHome implements Serializable{
	
	private static final long serialVersionUID = -8334641938717102784L;
	
	String cityExpName;// 定位城市
	List<TChannel> channelList = new ArrayList<TChannel>();
	TCityConfig cityConfig;
	TCityConfig countryConfig;
	List<BuildingExpStore> buildingExpStoreList = new ArrayList<BuildingExpStore>();
	Integer expStoreNum;
	int countrySuitNum;
	List<ExpStoreInfo> countryExpStoreList = new ArrayList<ExpStoreInfo>();
	List<ExpStoreInfo> currentCityExpStoreList = new ArrayList<ExpStoreInfo>();
	ExpStoreInfo newestExpStoreInfo;
	List<ExpStoreSuitDto> hotSuitList = new ArrayList<ExpStoreSuitDto>();
	List<TProductSummary> hotProductList = new ArrayList<TProductSummary>();
}