/**
 * 
 */
package com.ihomefnt.o2o.service.dao.house;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.house.HouseDao;
import com.ihomefnt.o2o.intf.domain.house.dto.House;

/**
 * @author Administrator
 *
 */
@Repository
public class HouseDaoImpl implements HouseDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.house.HouseDao.";
    

    /* (non-Javadoc)
     * @see com.ihomefnt.o2o.intf.dao.house.HouseDao#queryHouseById(java.lang.Long)
     */
    @Override
    public House queryHouseById(Long houseId) {
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryHouseById", houseId);
    }


}
