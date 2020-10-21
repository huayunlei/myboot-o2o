/**
 * 
 */
package com.ihomefnt.o2o.intf.dao.building;

import com.ihomefnt.o2o.intf.domain.building.doo.Building;

/**
 * @author Administrator
 *
 */
public interface BuildingDao {

    Building queryBuildingByHouseId(Long houseId);
    
    Building getBuildingById(Long buildingId);
}
