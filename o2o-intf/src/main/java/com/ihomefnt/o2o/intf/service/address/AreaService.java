package com.ihomefnt.o2o.intf.service.address;

import com.ihomefnt.o2o.intf.domain.address.dto.AreaDto;
import com.ihomefnt.o2o.intf.domain.address.dto.AreaInfoDto;
import com.ihomefnt.o2o.intf.domain.address.vo.response.ProvinceResponseVo;

import java.util.List;
import java.util.Map;

public interface AreaService {

	AreaDto queryCity(Long areaId);
	
	List<ProvinceResponseVo> queryAddress();

    AreaDto getArea(long areaId);

    String queryFullAddress(long areaId);

    AreaInfoDto getAreaInfo(Long areaId);

    Map<Long, AreaDto> getChildParentAreaMap();

    AreaInfoDto getAreaInfo(Long areaId, Map<Long, AreaDto> childParentAreaMap, Map<Long, AreaDto> areaMap);

}
