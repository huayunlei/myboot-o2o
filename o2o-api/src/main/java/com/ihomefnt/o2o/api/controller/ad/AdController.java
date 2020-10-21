package com.ihomefnt.o2o.api.controller.ad;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.ad.dto.AdvertisementDto;
import com.ihomefnt.o2o.intf.domain.ad.vo.request.AdQueryRequestVo;
import com.ihomefnt.o2o.intf.domain.ad.vo.request.AdStartPageRequestVo;
import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdStartPageItemResponseVo;
import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdStartPageResponseVo;
import com.ihomefnt.o2o.intf.domain.ad.vo.response.AdvertisementResponseVo;
import com.ihomefnt.o2o.intf.service.ad.AdService;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "【广告API】")
@RestController
@RequestMapping(value = "/ad")
public class AdController {
	
	@Autowired
	AdService adService; 

	@RequestMapping(value = "/getAd", method = RequestMethod.POST)
    public HttpBaseResponse<AdvertisementResponseVo> getAdById(@Json AdQueryRequestVo request) {
		if (request == null || null == request.getId()) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
        AdvertisementDto advertisement=adService.querAdById(request.getId());
        AdvertisementResponseVo adResponse=new AdvertisementResponseVo(advertisement);
        return HttpBaseResponse.success(adResponse);
	}
	
	@RequestMapping(value = "/getStartPage", method = RequestMethod.POST)
    public HttpBaseResponse<AdStartPageResponseVo> getStartPageById(@Json AdStartPageRequestVo request) {
		if (request == null || null == request.getOsType()) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, MessageConstant.DATA_TRANSFER_EMPTY);
		}
			
		List<AdStartPageItemResponseVo> startPageList = adService.queryStartPageList(request);
		return HttpBaseResponse.success(new AdStartPageResponseVo(startPageList));
	}
	
}
