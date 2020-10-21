/**
 * 
 */
package com.ihomefnt.o2o.intf.service.experiencestore;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.experiencestore.dto.ExpStoreHome;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreDetailResponse;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreResquest;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoresResponse;

/**
 * @author Administrator
 *
 */
public interface ExperienceStoreService {

    /**
     * 获取体验馆首页数据
     * 
     * @param latitude
     * @param longitude
     * @return
     */
    HttpExperienceStoresResponse getExperStores(Double latitude, Double longitude,String cityCode);

    /**
     * 默认
     * 
     * @return
     */
    HttpExperienceStoresResponse getExperStores(String cityCode);

    /**
     * 获取体验馆具体信息
     * 
     * @param dsId
     * @return
     */
    HttpExperienceStoreDetailResponse getDSDetail(Long dsId);
    
    List<HttpExperienceStoreDetailResponse> getDSDetailById(Long esId);

	HttpExperienceStoresResponse getExperStores200(HttpExperienceStoreResquest resquest);

	HttpExperienceStoreDetailResponse getDSDetail200(Long dsId);

	/**
	 * 2.6.0体验店列表新版.
	 * @param resquest
	 * @return
	 */
    HttpExperienceStoresResponse getExperStores260(HttpExperienceStoreResquest request);

	ExpStoreHome getHomeCache(Long areaId);
}
