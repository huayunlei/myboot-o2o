/**
 * 
 */
package com.ihomefnt.o2o.intf.dao.house;

import com.ihomefnt.o2o.intf.domain.house.dto.House;

/**
 * @author Administrator
 *
 */
public interface HouseDao {
    House queryHouseById(Long houseId);
}
