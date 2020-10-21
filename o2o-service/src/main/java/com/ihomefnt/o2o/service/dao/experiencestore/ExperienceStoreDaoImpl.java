/**
 * 
 */
package com.ihomefnt.o2o.service.dao.experiencestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.experiencestore.ExperienceStoreDao;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.ActivityLabel;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreDetailResponse;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.HttpExperienceStoreResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.Suit;
import com.ihomefnt.o2o.intf.domain.house.dto.House;

/**
 * @author Administrator
 *
 */
@Repository
public class ExperienceStoreDaoImpl implements ExperienceStoreDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.experiencestore.ExperienceStoreDao.";

    /* (non-Javadoc)
     * @see com.ihomefnt.o2oold.experiencestore.dao.ExperienceStoreDao#getExperStores(java.lang.Double, java.lang.Double)
     */
    @Override
    public List<HttpExperienceStoreResponse> getExperStores(Double latitude, Double longitude,String cityCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        if(null != cityCode && !"".equals(cityCode)){
        	map.put("cityCode", cityCode);
        } else {
        	map.put("cityCode", "210000");
        }
        return sqlSessionTemplate.selectList(NAME_SPACE + "getExperStoresFromPlace", map);
    }

    /* (non-Javadoc)
     * @see com.ihomefnt.o2oold.experiencestore.dao.ExperienceStoreDao#getExperStores()
     */
    @Override
    public List<HttpExperienceStoreResponse> getExperStores(String cityCode) {
    	Map<String, Object> map = new HashMap<String, Object>();
        if(null != cityCode && !"".equals(cityCode)){
        	map.put("cityCode", cityCode);
        } else {
        	map.put("cityCode", "210000");
        }
        return sqlSessionTemplate.selectList(NAME_SPACE + "getExperStores",map);
    }

    /* (non-Javadoc)
     * @see com.ihomefnt.o2oold.experiencestore.dao.ExperienceStoreDao#getDSDetail(java.lang.Long)
     */
    @Override
    public HttpExperienceStoreDetailResponse getDSDetail(Long dsId) {
        return sqlSessionTemplate.selectOne(NAME_SPACE + "getDSDetail", dsId);
    }

	@Override
	public List<HttpExperienceStoreDetailResponse> getDSDetailById(Long esId) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "getDSDetailById", esId);
	}
	
	@Override
    public List<HttpExperienceStoreResponse> getExperStores(Map<String, Object> map) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "getExperStores200", map);
    }
	
	@Override
    public List<HttpExperienceStoreResponse> getExperStoresFromPlace(Map<String, Object> map) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "getExperStoresFromPlace200", map);
    }

	@Override
	public int getExperStoresCount(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "getExperStoresCount", map);
	}

	@Override
	public int getHouseCount(Map<String, Object> map) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "getHouseCount", map);
	}

	@Override
	public List<HttpExperienceStoreResponse> getMostSuitList(Map<String, Object> map) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "getMostSuitList", map);
	}

	@Override
	public List<HttpExperienceStoreResponse> getMostSaleList(Map<String, Object> map) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "getMostSaleList", map);
	}

	@Override
	public HttpExperienceStoreDetailResponse getDSDetail200(Long dsId) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "getDSDetail200", dsId);
	}

	@Override
	public List<House> queryHouseByBuildingId(Long buildingId) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryHouseByBuildingId", buildingId);
	}

	@Override
	public List<Suit> querySuitByHouseId(Long houseId) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "querySuitByHouseId", houseId);
	}

    @Override
    public List<HttpExperienceStoreResponse> getExperStoresFromPlace260(Map<String, Object> map) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryExperStoresFromPlace260", map);
    }

    @Override
    public List<HttpExperienceStoreResponse> getExperStores260(Map<String, Object> map) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryExperStores260", map);
    }

    @Override
    public List<ActivityLabel> getExperStoresActivity() {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryExperStoresActivity");
    }

	@Override
	public List<ActivityLabel> queryExperienceStoreActivity(Long dsId) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryExperienceStoreActivity", dsId);
	}

	@Override
	public HttpExperienceStoreDetailResponse queryNearestAddress(
			Double latitude, Double longitude) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("latitude", latitude);
        map.put("longitude", longitude);
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryNearestAddress", map);
	}

}
