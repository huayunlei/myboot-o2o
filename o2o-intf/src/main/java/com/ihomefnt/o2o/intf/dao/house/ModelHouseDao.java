package com.ihomefnt.o2o.intf.dao.house;

import com.ihomefnt.o2o.intf.domain.building.doo.Building;
import com.ihomefnt.o2o.intf.domain.house.dto.THouse;
import com.ihomefnt.o2o.intf.domain.suit.dto.TSuit;
import com.ihomefnt.o2o.intf.domain.house.dto.TModelHouses;

import java.util.List;

public interface ModelHouseDao {

	Long enrollModelHouse(TModelHouses house);
	
	List<String> queryEnrollList();
	
	Building queryBuildingById(Long houseId);
	
	THouse queryHouseById(Long houseId);
	
	List<TSuit> querySuitList(Long houseId);
}
