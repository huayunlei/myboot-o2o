package com.ihomefnt.o2o.intf.dao.address;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.address.doo.CityConfigDo;
import com.ihomefnt.o2o.intf.domain.address.doo.CityDo;

public interface ChannelDao {
	List<CityDo> queryChannel();

    CityDo queryDefaultChannel();

    List<CityDo> locationCity(Double latitude, Double longitude);
    
    CityConfigDo getCityConfigByCode(String cityCode);
}
