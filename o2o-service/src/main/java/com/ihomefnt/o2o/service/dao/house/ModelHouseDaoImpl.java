package com.ihomefnt.o2o.service.dao.house;

import com.ihomefnt.o2o.intf.dao.house.ModelHouseDao;
import com.ihomefnt.o2o.intf.domain.building.doo.Building;
import com.ihomefnt.o2o.intf.domain.house.dto.THouse;
import com.ihomefnt.o2o.intf.domain.house.dto.TModelHouses;
import com.ihomefnt.o2o.intf.domain.suit.dto.TSuit;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelHouseDaoImpl implements ModelHouseDao{

    @Autowired
    private SqlSessionTemplate sqlSession;
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.house.ModelHouseDao.";
	@Override
	public Long enrollModelHouse(TModelHouses house) {
        sqlSession.insert(NAME_SPACE + "enrollModelHouse", house);
        return house.getId();
	}
	@Override
	public List<String> queryEnrollList() {
        return sqlSession.selectList(NAME_SPACE + "queryEnrollList");
	}
	
	@Override
	public THouse queryHouseById(Long houseId) {
		return sqlSession.selectOne(NAME_SPACE +"queryHouseById",houseId);
	}
	@Override
	public List<TSuit> querySuitList(Long houseId) {
		return sqlSession.selectList(NAME_SPACE +"querySuitList", houseId);
	}
	@Override
	public Building queryBuildingById(Long houseId) {
			return sqlSession.selectOne(NAME_SPACE +"queryBuildingById",houseId);
	}
}
