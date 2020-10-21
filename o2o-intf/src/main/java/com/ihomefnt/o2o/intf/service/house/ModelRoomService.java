/**
 * 
 */
package com.ihomefnt.o2o.intf.service.house;

import com.ihomefnt.o2o.intf.domain.house.dto.ModelRoomDto;
import com.ihomefnt.o2o.intf.domain.house.vo.request.HttpModeRoomRequest;

/**
 * @author weitichao
 * h5 3d体验店
 *
 */
public interface ModelRoomService {
	
	/**
	 * 获取3V家套装信息
	 */
	ModelRoomDto getModelRoomList(HttpModeRoomRequest request);
}
