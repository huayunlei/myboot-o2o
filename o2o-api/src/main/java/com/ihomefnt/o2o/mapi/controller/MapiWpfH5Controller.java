package com.ihomefnt.o2o.mapi.controller;

import com.google.common.collect.ImmutableList;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.suit.dto.*;
import com.ihomefnt.o2o.intf.domain.user.doo.UserVisitLogDo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpMessage;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.suit.dto.*;
import com.ihomefnt.o2o.intf.domain.user.doo.UserVisitLogDo;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.customer.CustomerConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.suit.WpfSuitService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * H5页面全品家接口
 * @author weitichao
 *
 */
@Api(value="M站全品家API",description="M站全品家老接口",tags = "【M-API】")
@Controller
@RequestMapping(value = "/mapi/wpfh5")
public class MapiWpfH5Controller {
	
	private List<String> buildingList1 = ImmutableList.of("中南世纪雅苑", "中南山锦花城", "中南锦苑", "中南锦城");
	private List<String> buildingList2 = ImmutableList.of("昆山鹿鸣九里");
	private List<String> buildingList3 = ImmutableList.of("濮阳建业桂园", "濮阳建业壹号城邦");
	private List<String> buildingList4 = ImmutableList.of("新乡建业壹号城邦", "新乡建业联盟新城");
	private List<String> buildingList5 = ImmutableList.of("许昌建业壹号城邦", "许昌长葛建业桂园");
	private List<String> buildingList6 = ImmutableList.of("周口建业森林半岛");
	private List<String> buildingList7 = ImmutableList.of("鹿邑建业城");
	private List<String> buildingList8 = ImmutableList.of("永城建业联盟新城");
	private List<String> buildingList9 = ImmutableList.of("巩义建业壹号城邦");
	private List<String> buildingList10 = ImmutableList.of("淮阳建业桂园");
	private List<String> buildingList11 = ImmutableList.of("扶沟建业城");
	private List<String> buildingList12 = ImmutableList.of("海南中南西海岸");
	
	
	private List<TCity> cityList = ImmutableList.of(
			new TCity(1, "南京",buildingList1),
			new TCity(2, "新乡",buildingList4),
			new TCity(3, "濮阳",buildingList3),
			new TCity(4, "许昌",buildingList5),
			new TCity(5, "周口",buildingList6),
			new TCity(6, "昆山", buildingList2),
			new TCity(7, "永城", buildingList8),
			new TCity(8, "鹿邑", buildingList7),
			new TCity(9, "巩义", buildingList9),
			new TCity(10, "淮阳", buildingList10),
			new TCity(11, "扶沟", buildingList11),
			new TCity(12, "儋州", buildingList12));

	@Autowired
	WpfSuitService wpfSuitService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserProxy userProxy;
	
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public HttpBaseResponse<HttpWpfSuitListResponse> getWpfSuitList(){
		
		UserVisitLogDo userVisitLog = new UserVisitLogDo("", "", 9, "打开h5全品家列表", 30, 0l);
        userService.saveUserVisitLog(userVisitLog);
        HttpWpfSuitListResponse response = new HttpWpfSuitListResponse();
		
        List<TWpfSuit> wpfSuitList = wpfSuitService.getWpfSuitList();
        response.setWpfSuitList(wpfSuitList);
        
        List<WpfCaseItem> wpfCaseList = wpfSuitService.getWpfCaseList();
        response.setWpfCaseList(wpfCaseList);
        return HttpBaseResponse.success(response);
	}
	
	@RequestMapping(value = "/detail",method = RequestMethod.POST)
	public HttpBaseResponse<TWpfSuit> getWpfSuitDetail(@Json HttpWpfSuitRequest request){
		if(null == request || null == request.getWpfSuitId()){
			return HttpBaseResponse.fail(CustomerConstant.MESSAGE_PARAMETERS_DATA_EMPTY);
		}

		if(request.getWpfSuitId() == 1){
			UserVisitLogDo userVisitLog = new UserVisitLogDo("", "", 9, "打开h5全品家1314详情", 31, 1l);
			userService.saveUserVisitLog(userVisitLog);
		}else if(request.getWpfSuitId() == 2){
			UserVisitLogDo userVisitLog = new UserVisitLogDo("", "", 9, "打开h5全品家1912详情", 31, 2l);
			userService.saveUserVisitLog(userVisitLog);
		}else if(request.getWpfSuitId() == 3){
			UserVisitLogDo userVisitLog = new UserVisitLogDo("", "", 9, "打开h5全品家2399详情", 31, 3l);
			userService.saveUserVisitLog(userVisitLog);
		}else if(request.getWpfSuitId() == 4){
			UserVisitLogDo userVisitLog = new UserVisitLogDo("", "", 9, "打开h5全品家2999详情", 31, 4l);
			userService.saveUserVisitLog(userVisitLog);
		}

		TWpfSuit wpfSuitDetail = wpfSuitService.getWpfSuitDetail(request);

		List<TWpfStyleImage> wpfSuitBomImageList = wpfSuitService.getWpfSuitBomImage(request);
		wpfSuitDetail.setWpfSuitBomImageList(wpfSuitBomImageList);
		List<TWpfSuitAd> wpfSuitAdList = wpfSuitService.getWpfSuitAd();
		wpfSuitDetail.setSuitAdList(wpfSuitAdList);

		return HttpBaseResponse.success(wpfSuitDetail);
	}
	
	
	
	@RequestMapping(value = "/appointmentService", method = RequestMethod.POST)
	public HttpBaseResponse<String> setAppointmentService(@Json HttpWpfAppointmentRequest request){
		if (request == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
        if (null == userDto) {
            // does not exist or match
			return HttpBaseResponse.fail(MessageConstant.ADMIN_ILLEGAL);
        }
		request.setName(userDto.getUsername());
		request.setPhoneNum(userDto.getMobile());
		String result = wpfSuitService.setWpfAppointment(request);
		return HttpBaseResponse.success(result);
	}
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public HttpBaseResponse<Boolean> submitOrder(@Json HttpWpfSubmitOrderRequest request){
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (null == userDto) {
			// does not exist or match
			return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
		}

		request.setSubmitMobile(userDto.getMobile());
		boolean result = wpfSuitService.submitWpfOrder(request);
		if(!result) {
			return HttpBaseResponse.fail(MessageConstant.FAILED);
		}
		return HttpBaseResponse.success(result);
	}
	
	@RequestMapping(value = "/getCities", method = RequestMethod.POST)
	public HttpBaseResponse<Map<String, Object>> getCities(@Json HttpBaseRequest request){
		if (request == null) {
			return HttpBaseResponse.fail(HttpResponseCode.PARAMS_NOT_EXISTS, MessageConstant.DATA_TRANSFER_EMPTY);
		}
		HttpUserInfoRequest userDto = request.getUserInfo();
		if (null == userDto) {
			// does not exist or match
			return HttpBaseResponse.fail(HttpResponseCode.USER_NOT_LOGIN, MessageConstant.ADMIN_ILLEGAL);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("cityList", cityList);
		return HttpBaseResponse.success(resultMap);
	}
	
}
