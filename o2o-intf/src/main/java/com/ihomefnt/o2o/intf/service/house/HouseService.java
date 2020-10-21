/**
 * 
 */
package com.ihomefnt.o2o.intf.service.house;

import com.ihomefnt.o2o.intf.domain.house.dto.House;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AladdinHouseInfoResponseVo;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface HouseService {

    House queryHouseById(Long houseId);

    // 根据用户ID查询用户房产信息
    List<HouseInfoResponseVo> queryUserHouseList(Integer userId);

    AladdinHouseInfoResponseVo queryHouseByHouseId(Integer houseId);

}
