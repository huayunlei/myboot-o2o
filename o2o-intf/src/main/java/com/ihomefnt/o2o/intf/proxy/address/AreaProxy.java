package com.ihomefnt.o2o.intf.proxy.address;

import com.ihomefnt.o2o.intf.domain.address.dto.AreaDto;
import com.ihomefnt.o2o.intf.domain.address.dto.ProvinceDto;

import java.util.List;

public interface AreaProxy {

	AreaDto queryArea(Long areaId);

	List<ProvinceDto> queryAddress();

    List<AreaDto> queryAreaList();

	 String queryFullAddress(long areaId);

}
