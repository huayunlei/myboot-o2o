package com.ihomefnt.o2o.intf.service.address;

import com.ihomefnt.o2o.intf.domain.address.dto.CityConfigDto;
import com.ihomefnt.o2o.intf.domain.address.vo.request.CityRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.CityListResponseVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.LocationCityResponseVo;

public interface CityService {

    CityListResponseVo queryChannel(CityRequestVo channelRequest);

    LocationCityResponseVo locationCity(Double latitude, Double longitude);

    LocationCityResponseVo queryDefaultChannel();
    
    CityConfigDto getCityConfigByCode(String cityCode);
}
