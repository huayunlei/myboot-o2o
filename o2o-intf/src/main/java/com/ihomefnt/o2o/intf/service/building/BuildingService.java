/**
 * 
 */
package com.ihomefnt.o2o.intf.service.building;

import com.ihomefnt.o2o.intf.domain.building.doo.Building;

/**
 * @author Administrator
 *
 */
public interface BuildingService {
    Building queryBuildingByHouseId(Long houseId);
}
