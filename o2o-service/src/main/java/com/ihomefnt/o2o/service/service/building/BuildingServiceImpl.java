/**
 * 
 */
package com.ihomefnt.o2o.service.service.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.service.building.BuildingService;
import com.ihomefnt.o2o.intf.dao.building.BuildingDao;
import com.ihomefnt.o2o.intf.domain.building.doo.Building;

/**
 * @author Administrator
 *
 */
@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    BuildingDao buildingDao;
    
    @Override
    public Building queryBuildingByHouseId(Long houseId) {
        return buildingDao.queryBuildingByHouseId(houseId);
    }


}
