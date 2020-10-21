package com.ihomefnt.o2o.api.controller.suit;

import com.ihomefnt.common.util.image.ImageQuality;
import com.ihomefnt.common.util.image.ImageTool;
import com.ihomefnt.common.util.image.ImageType;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.suit.dto.Room;
import com.ihomefnt.o2o.intf.domain.suit.dto.Suit;
import com.ihomefnt.o2o.intf.domain.suit.vo.request.HttpRoomRequest;
import com.ihomefnt.o2o.intf.domain.suit.vo.response.HttpRoomResponse;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.constant.suit.SuitConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.suit.SuitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author wang<br/>
 *         空间控制器<br/>
 *
 */
@ApiIgnore
@Deprecated
@Api(tags = "空间API",hidden = true)
@RestController
@RequestMapping(value = "/room")
public class RoomController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RoomController.class); //日志

	@Autowired
	SuitService suitService; // 注入service

	/**
	 * 获取新版空间详情
	 */
	@ApiOperation(value = "roomInfoTab", notes = "获取新版空间详情")
	@RequestMapping(value = "/roomInfoTab", method = RequestMethod.POST)	
	public HttpBaseResponse<HttpRoomResponse> roomInfoTab (@Json HttpRoomRequest httpRoomRequest){
		if (httpRoomRequest == null || httpRoomRequest.getRoomId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, SuitConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}

		Long roomId = httpRoomRequest.getRoomId();
		Room roomInfo = suitService.getRoomInfoByRoomId(roomId);
		Suit suitInfo = suitService.getSuitInfoByRoomId(roomId);

		roomInfo.setSuitInfo(suitInfo);
		if(suitInfo!=null){
			roomInfo.setSuitStyle(suitInfo.getSuitStyle());
		}
		List<Room> recommendRoom = suitService.getRecommendRoomList(roomId,httpRoomRequest.getCityCode());
		for(Room room :recommendRoom){
			String firstImage=room.getFirstImage();
			if(StringUtils.isNotBlank(firstImage)){
				room.setFirstImage(firstImage+ImageTool.appendTypeAndQuality(ImageType.SMALL, ImageQuality.HIGH));
			}			
		}
		
        HttpRoomResponse response =new HttpRoomResponse();
		response.setRoomInfo(roomInfo);
		response.setRoomList(recommendRoom);
		response.setServiceUrl(StaticResourceConstants.SERVICE_PROMISE_URL);
		response.setServicePicRatio(SuitConstant.SERVICE_PIC_RATIO);
		return HttpBaseResponse.success(response);
	}
	
	/**
	 * 获取空间商品列表
	 */
	@ApiOperation(value = "productList", notes = "获取空间商品列表")
	@RequestMapping(value = "/productList", method = RequestMethod.POST)	
	public HttpBaseResponse<HttpRoomResponse> productList (@Json HttpRoomRequest httpRoomRequest){
		if (httpRoomRequest == null || httpRoomRequest.getRoomId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, SuitConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}

		Long roomId = httpRoomRequest.getRoomId();
		Room roomInfo = suitService.getProductListByRoomId(roomId);
        
        HttpRoomResponse response = new HttpRoomResponse();
		response.setRoomInfo(roomInfo);
		return HttpBaseResponse.success(response);
	}
	
}
