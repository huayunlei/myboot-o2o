/**
 * 
 */
package com.ihomefnt.o2o.service.dao.building;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.building.BuildingDao;
import com.ihomefnt.o2o.intf.domain.building.doo.Building;

/**
 * @author Administrator
 *
 */
@Repository
public class BuildingDaoImpl implements BuildingDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.building.BuildingDao.";

    /* (non-Javadoc)
     * @see com.ihomefnt.o2o.intf.dao.building.BuildingDao#queryBuildingByHouseId(java.lang.Long)
     */
    @Override
    public Building queryBuildingByHouseId(Long houseId) {
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryBuildingByHouseId", houseId);
    }

    @Override
    public Building getBuildingById(Long buildingId) {
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryBuildingById", buildingId);
    }

    
}
