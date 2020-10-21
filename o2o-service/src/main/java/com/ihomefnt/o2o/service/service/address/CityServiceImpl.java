package com.ihomefnt.o2o.service.service.address;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.util.ModelMapperUtil;
import com.ihomefnt.o2o.intf.dao.address.ChannelDao;
import com.ihomefnt.o2o.intf.domain.address.doo.CityConfigDo;
import com.ihomefnt.o2o.intf.domain.address.doo.CityDo;
import com.ihomefnt.o2o.intf.domain.address.dto.CityConfigDto;
import com.ihomefnt.o2o.intf.domain.address.vo.request.CityRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.CityListResponseVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.CityDetailResponseVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.LocationCityResponseVo;
import com.ihomefnt.o2o.intf.service.address.CityService;

@Service
public class CityServiceImpl implements CityService{
	
	@Autowired
	ChannelDao channelDao;

	@Override
    public CityListResponseVo queryChannel(CityRequestVo channelRequest) {
		CityListResponseVo res = new CityListResponseVo();
		List<CityDetailResponseVo> cityList = new ArrayList<CityDetailResponseVo>();
		List<CityDo> list = channelDao.queryChannel();
		if(null != list && list.size() > 0){
			for(CityDo t : list){
				if(null != t){
					CityDetailResponseVo c = new CityDetailResponseVo();
					c.setCityCode(t.getChannelCode());
					c.setCityName(t.getChannelName());
                    c.setTelephone(t.getTelephone());
					cityList.add(c);
					if(null != t.getIsDefault() && t.getIsDefault() == 2){//默认城市
						res.setDefaultCity(c);
					}
				}
			}
		}
        if (channelRequest != null && null != channelRequest.getLatitude()
                && null != channelRequest.getLongitude()) {
            List<CityDo> channels = channelDao.locationCity(channelRequest.getLatitude(),
                    channelRequest.getLongitude());
            if (channels != null && !channels.isEmpty()) {
                CityDetailResponseVo c = new CityDetailResponseVo();
                c.setCityCode(channels.get(0).getChannelCode());
                c.setCityName(channels.get(0).getChannelName());
                c.setTelephone(channels.get(0).getTelephone());
                res.setDefaultCity(c);
            }
        }
		res.setCityList(cityList);
		return res;
	}

    @Override
    public LocationCityResponseVo locationCity(Double latitude, Double longitude) {
    	CityDo cityDo = null;
        List<CityDo> channels = channelDao.locationCity(latitude, longitude);
        if (channels != null && !channels.isEmpty()) {
        	cityDo = channels.get(0);
        } else {
        	cityDo = channelDao.queryDefaultChannel();
        }
        return ModelMapperUtil.strictMap(cityDo, LocationCityResponseVo.class);
    }

    @Override
    public LocationCityResponseVo queryDefaultChannel() {
    	CityDo cityDo =	channelDao.queryDefaultChannel();
    	if (null == cityDo) {
    		return null;
    	}
        return ModelMapperUtil.strictMap(cityDo, LocationCityResponseVo.class);
    }

	@Override
	public CityConfigDto getCityConfigByCode(String cityCode) {
		CityConfigDo cityConfigDo = channelDao.getCityConfigByCode(cityCode);
		if (null == cityConfigDo) {
    		return null;
    	}
        return ModelMapperUtil.strictMap(cityConfigDo, CityConfigDto.class);
	}
}
