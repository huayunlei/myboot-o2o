package com.ihomefnt.o2o.intf.service.partners;

import com.ihomefnt.o2o.intf.domain.partners.dto.Area;
import com.ihomefnt.o2o.intf.domain.partners.dto.TPartners;

import java.util.List;

public interface PartnersService {

	Long enrollPartners(TPartners partners);
	
	List<String> queryEnrollList();
	
	String queryEnrollByMobile(String mobile);
	
	List<Area> queryAreaBuilding(String cityCode);
}
