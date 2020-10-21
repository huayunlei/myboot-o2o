/**
 * 
 */
package com.ihomefnt.o2o.api.controller.experiencestore;

import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.domain.address.dto.CityConfigDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.experiencestore.dto.ExpStoreHome;
import com.ihomefnt.o2o.intf.domain.experiencestore.dto.ExpStoreInfo;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreDetailResponse;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreDetailResquest;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreResquest;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoresResponse;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.address.CityService;
import com.ihomefnt.o2o.intf.service.experiencestore.ExperienceStoreService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */

@Api(tags = "【体验店API】")
@RestController
@RequestMapping(value = "/experstore")
public class ExperienceStoreController {
    @Autowired
    ExperienceStoreService experienceStoreService;
    @Autowired
    CityService channelService;
    /**
     * 体验店列表
     * 
     * @return
     */
    @PostMapping(value = "/getExperStores")
    public HttpBaseResponse<HttpExperienceStoresResponse> getExperStores(@Json HttpExperienceStoreResquest request) {
    	if (request == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
    	
        HttpExperienceStoresResponse experienceStoreResponse;
        if (null != request.getLatitude() && null != request.getLongitude()) {
            //根据当前位置去取
            experienceStoreResponse = experienceStoreService.getExperStores(request.getLatitude(),
                    request.getLongitude(),request.getCityCode());
        } else {
            //获得默认的值
            experienceStoreResponse = experienceStoreService.getExperStores(request.getCityCode());
        }
        return HttpBaseResponse.success(experienceStoreResponse);
    }

    /**
     * 
     * @param resquest
     * @return
     */
    @PostMapping(value = "/getDSDetail")
    public HttpBaseResponse<HttpExperienceStoreDetailResponse> getDSDetail(@Json HttpExperienceStoreDetailResquest resquest) {
    	if (resquest == null || resquest.getEsId() == null) {
    		return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
    	}
        HttpExperienceStoreDetailResponse experienceStoreInfo = experienceStoreService.getDSDetail(resquest.getEsId());
        return HttpBaseResponse.success(experienceStoreInfo);
    }
    
    /**
     * 体验店列表
     */
    @PostMapping(value = "/getExperStores200")
    public HttpBaseResponse<HttpExperienceStoresResponse> getExperStores200(@Json HttpExperienceStoreResquest resquest) {
		if (resquest == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}    	
        HttpExperienceStoresResponse experienceStoreResponse = experienceStoreService.getExperStores200(resquest);
        return HttpBaseResponse.success(experienceStoreResponse);

    }

    /**
     * 体验店详情
     */
    @PostMapping(value = "/getDSDetail200")
    public HttpBaseResponse<HttpExperienceStoreDetailResponse> getDSDetail200(@Json HttpExperienceStoreDetailResquest resquest) {
        if (resquest == null || resquest.getEsId() == null) {
        	return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        HttpExperienceStoreDetailResponse experienceStoreInfo = experienceStoreService.getDSDetail200(resquest.getEsId());
        return HttpBaseResponse.success(experienceStoreInfo);
    }
    
    
    @PostMapping(value = "/experstores260")
    public HttpBaseResponse<HttpExperienceStoresResponse> getExperStores260(@Json HttpExperienceStoreResquest resquest) {
    	if (resquest == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}  
        HttpExperienceStoresResponse experienceStoreResponse = experienceStoreService.getExperStores260(resquest);
        return HttpBaseResponse.success(experienceStoreResponse);
    }
    
    

    
    /**
     * 热门推荐
     */
    @PostMapping(value = "/hot")
    public HttpBaseResponse<List<ExpStoreInfo>> hot(@Json HttpBaseRequest resquest) {
    	if (resquest == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		} 
    	//获取地区对应的缓存
    	String cityCode="210000";
    	if(StringUtil.isNotBlank(resquest.getCityCode())){
    		cityCode=resquest.getCityCode();
    	}
    	CityConfigDto config = channelService.getCityConfigByCode(cityCode);
    	if(config!=null){
    		ExpStoreHome home = experienceStoreService.getHomeCache(config.getAreaId());
    		if(home!=null){
    			List<ExpStoreInfo> currentCityExpStoreList = home.getCurrentCityExpStoreList();
    			List<ExpStoreInfo> storeList = new ArrayList<>();
    			if(currentCityExpStoreList.size()>2){
    				storeList.add(currentCityExpStoreList.get(0));
    				storeList.add(currentCityExpStoreList.get(1));
    			}else{
    				storeList = currentCityExpStoreList;
    			}
    			return HttpBaseResponse.success(storeList);
    		}
    	}
    	return HttpBaseResponse.success(null);
    }
    
    
    @PostMapping(value = "/search")
    public HttpBaseResponse<HttpExperienceStoresResponse> experStoreSearch(@Json HttpExperienceStoreResquest resquest) {
        return this.getExperStores200(resquest);
    }
    
}
