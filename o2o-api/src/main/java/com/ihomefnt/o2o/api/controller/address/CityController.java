package com.ihomefnt.o2o.api.controller.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ihomefnt.o2o.intf.domain.address.vo.request.CityRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.CityListResponseVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.LocationCityResponseVo;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreResquest;
import com.ihomefnt.o2o.intf.service.address.CityService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import io.swagger.annotations.Api;

@Api(tags = "【城市API】")
@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	CityService cityService;
	
    @RequestMapping(value = "/getCityList", method = RequestMethod.POST)
    public HttpBaseResponse<CityListResponseVo> getCityList(@Json CityRequestVo request) {
    	if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}

        CityListResponseVo response = cityService.queryChannel(request);
        response.setPrompt("其他城市正在积极筹建中,敬请期待。");
		return HttpBaseResponse.success(response);
    }

    @RequestMapping(value = "/locationCity", method = RequestMethod.POST)
    public HttpBaseResponse<LocationCityResponseVo> locationCity(@Json HttpExperienceStoreResquest request) {
    	if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
    	
    	LocationCityResponseVo locationCityResponseVo = null;
        if (request != null && null != request.getLatitude() && null != request.getLongitude()) {
            //根据当前位置去取
            locationCityResponseVo = cityService.locationCity(request.getLatitude(),
                    request.getLongitude());
        } else {
            //获得默认的值
            locationCityResponseVo = cityService.queryDefaultChannel();
        }
		return HttpBaseResponse.success(locationCityResponseVo);
    }
	
}
