/**
 * 
 */
package com.ihomefnt.o2o.api.controller.suit;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.suit.dto.Room;
import com.ihomefnt.o2o.intf.domain.suit.dto.RoomImage;
import com.ihomefnt.o2o.intf.domain.suit.vo.request.HttpSuitRequest;
import com.ihomefnt.o2o.intf.domain.suit.vo.response.HttpSuitResponse;
import com.ihomefnt.o2o.intf.manager.constant.suit.SuitConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.service.suit.SuitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang<br/>
 *         套装控制器<br/>
 *
 */
@ApiIgnore
@Deprecated
@Api(tags = "套装老API",hidden = true)
@RestController
@RequestMapping(value = "/suit")
public class SuitController {
	
	@Autowired
	SuitService suitService; // 注入service

	/**
	 * 获取空间下的所有列表图片列表
	 */
	@ApiOperation(value = "getRoomImageList", notes = "获取空间下的所有列表图片列表")
	@RequestMapping(value = "/getRoomImageList", method = RequestMethod.POST)	
	public HttpBaseResponse<HttpSuitResponse> getRoomImageList (@Json HttpSuitRequest  httpSuitRequest ){
		//对前台传入参数做保护
		if (httpSuitRequest == null || httpSuitRequest.getSuitId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, SuitConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}
		Long suitId =httpSuitRequest.getSuitId();	
		List<Room> roomList= suitService.getRoomListBySuitId(suitId);
		//对后台查询结果做保护
		if (roomList == null||roomList.isEmpty()) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, SuitConstant.MESSAGE_RESULT_DATA_EMPTY);
		}
		for (Room room : roomList) {
			Long roomId = room.getRoomId();
			List<RoomImage> roomImageList = suitService.getRoomImageListByRoomIdAndDetailPage(roomId,SuitConstant.DESIGN_STATUS_NO,null);
			// 对空间图片做保护
			if (roomImageList != null && !roomImageList.isEmpty()) {
				room.setImagelist(roomImageList);
			}
		}		
        HttpSuitResponse httpSuitResponse  =new HttpSuitResponse();
        httpSuitResponse.setRoomList(roomList);
		return HttpBaseResponse.success(httpSuitResponse);
	}
	
	/**
	 * 套装详情
	 * @param httpSuitRequest
	 * @return
	 */
	@ApiOperation(value = "getSuitInfo", notes = "套装详情")
	@RequestMapping(value = "/getSuitInfo", method = RequestMethod.POST)	
	public HttpBaseResponse<HttpSuitResponse> getSuitInfo (@Json HttpSuitRequest httpSuitRequest){
		//对前台传入参数做保护
		if (httpSuitRequest == null || httpSuitRequest.getSuitId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, SuitConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}

		HttpSuitResponse httpSuitResponse = suitService.getSuitInfoBySuitId(httpSuitRequest.getSuitId());
		return HttpBaseResponse.success(httpSuitResponse);
	}
	
	/**
	 * 套装物品清单详情
	 * @param httpSuitRequest
	 * @return
	 */
	@ApiOperation(value = "getProductsInfo", notes = "套装物品清单详情")
	@RequestMapping(value = "/getProductsInfo", method = RequestMethod.POST)	
	public HttpBaseResponse<HttpSuitResponse> getProductsInfo (@Json HttpSuitRequest httpSuitRequest){
		if (httpSuitRequest == null || httpSuitRequest.getSuitId() == null) {
			return HttpBaseResponse.fail(HttpResponseCode.FAILED, SuitConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}
		Long suitId =httpSuitRequest.getSuitId();	
		List<Room> roomList= suitService.getRoomListBySuitId(suitId);
		List<Room> targetList= new ArrayList<Room>();
		//对空间做保护
		if(roomList!=null&&!roomList.isEmpty()){
			for(Room room:roomList){
				Long roomId =room.getRoomId();
				Room target = suitService.getProductListByRoomIdAndSuitId(roomId,suitId);
				if(null != target) {
					target.setRoomPrice(room.getRoomPrice());
				} else {
					target = new Room();
					target.setRoomPrice(room.getRoomPrice());
				}
				targetList.add(target);
			}
		}
		HttpSuitResponse httpSuitResponse = new HttpSuitResponse();
		httpSuitResponse.setRoomList(targetList);
		return HttpBaseResponse.success(httpSuitResponse);
	}
	
}
