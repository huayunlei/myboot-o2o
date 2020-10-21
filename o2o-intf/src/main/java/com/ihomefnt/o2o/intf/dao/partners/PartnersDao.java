package com.ihomefnt.o2o.intf.dao.partners;

import com.ihomefnt.o2o.intf.domain.partners.dto.Area;
import com.ihomefnt.o2o.intf.domain.partners.dto.TPartners;

import java.util.List;
import java.util.Map;

public interface PartnersDao {

	Long enrollPartners(TPartners partners);
	
	List<String> queryEnrollList();
	
	String queryEnrollByMobile(String mobile);
	
	List<Area> queryAreaBuilding(Map<String,Object> params);
}
