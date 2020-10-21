/**
 * 
 */
package com.ihomefnt.o2o.intf.dao.house;

import com.ihomefnt.o2o.intf.domain.house.dto.ModelRoomDto;
import com.ihomefnt.o2o.intf.domain.house.vo.request.HttpModeRoomRequest;

/**
 * @author weitichao
 *
 */
public interface ModelRoomDao {

	/**
	 * 获取h5 3v家信息
	 * @param request
	 * @return
	 */
	ModelRoomDto getModelRoomList(HttpModeRoomRequest request);
	
	/**
	 * 获取h3 3v家线下体验店数量
	 * @param request
	 * @return
	 */
	int getModelRoomOfflineNum(HttpModeRoomRequest request);
	
	/**
	 * 获取h5 3v家体验店信息
	 * @param request
	 * @return
	 */
	ModelRoomDto getModelRoomInfo(HttpModeRoomRequest request);
}
