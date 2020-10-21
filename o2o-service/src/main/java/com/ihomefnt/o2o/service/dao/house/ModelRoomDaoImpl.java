/**
 * 
 */
package com.ihomefnt.o2o.service.dao.house;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.house.ModelRoomDao;
import com.ihomefnt.o2o.intf.domain.house.dto.ModelRoomDto;
import com.ihomefnt.o2o.intf.domain.house.vo.request.HttpModeRoomRequest;

/**
 * @author weitichao
 *
 */

@Repository
public class ModelRoomDaoImpl implements ModelRoomDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.house.ModelRoomDao.";
	
	@Override
	public ModelRoomDto getModelRoomList(HttpModeRoomRequest request) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryModelRoomList", request);
	}

	@Override
	public int getModelRoomOfflineNum(HttpModeRoomRequest request) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryModelRoomNum", request);
	}

	@Override
	public ModelRoomDto getModelRoomInfo(HttpModeRoomRequest request) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryModelRoomInfo", request);
	}
	
	
	
}
