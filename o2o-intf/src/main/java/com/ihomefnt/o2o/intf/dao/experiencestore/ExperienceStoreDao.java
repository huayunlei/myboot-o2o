/**
 * 
 */
package com.ihomefnt.o2o.intf.dao.experiencestore;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.ActivityLabel;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreDetailResponse;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.Suit;
import com.ihomefnt.o2o.intf.domain.house.dto.House;

/**
 * @author Administrator
 *
 */
public interface ExperienceStoreDao {
    /**
     * 获取体验馆首页数据
     * 
     * @param latitude
     * @param longitude
     * @return
     */
    List<HttpExperienceStoreResponse> getExperStores(Double latitude, Double longitude,String cityCode);

    /**
     * 默认
     * 
     * @return
     */
    List<HttpExperienceStoreResponse> getExperStores(String cityCode);

    /**
     * 获取体验馆具体信息
     * 
     * @param dsId
     * @return
     */
    HttpExperienceStoreDetailResponse getDSDetail(Long dsId);
    
    
    /**
     * 获取体验馆具体信息
     * 
     * @param dsId
     * @return
     */
    List<HttpExperienceStoreDetailResponse> getDSDetailById(Long esId);

	List<HttpExperienceStoreResponse> getExperStores(Map<String, Object> map);

	List<HttpExperienceStoreResponse> getExperStoresFromPlace(Map<String, Object> map);

	int getExperStoresCount(Map<String, Object> map);

	int getHouseCount(Map<String, Object> map);

	List<HttpExperienceStoreResponse> getMostSuitList(Map<String, Object> map);

	List<HttpExperienceStoreResponse> getMostSaleList(Map<String, Object> map);

	HttpExperienceStoreDetailResponse getDSDetail200(Long dsId);

	List<House> queryHouseByBuildingId(Long buildingId);

	List<Suit> querySuitByHouseId(Long idtHouse);

	/**
	 * 根据经度纬度获取体验店列表信息.
	 * @param map
	 * @return
	 */
    List<HttpExperienceStoreResponse> getExperStoresFromPlace260(Map<String, Object> map);

    /**
     * 获取体验店列表信息.
     * @param map
     * @return
     */
    List<HttpExperienceStoreResponse> getExperStores260(Map<String, Object> map);

    List<ActivityLabel> getExperStoresActivity();
	
	/**
	 * 获取体验店的活动信息
	 * @author weitichao
	 * @return 
	 */
	List<ActivityLabel> queryExperienceStoreActivity(Long dsId);
	
	/**
	 * 查询距离线上体验店最近的线下体验店
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	HttpExperienceStoreDetailResponse queryNearestAddress(Double latitude,Double longitude);
	
}
