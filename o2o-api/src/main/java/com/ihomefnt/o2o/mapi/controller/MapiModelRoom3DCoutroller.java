/**
 * 
 */
package com.ihomefnt.o2o.mapi.controller;

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

import com.ihomefnt.common.http.HttpMessage;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.service.house.ModelRoomService;
import com.ihomefnt.o2o.intf.domain.house.dto.ModelRoomDto;
import com.ihomefnt.o2o.intf.domain.house.vo.request.HttpModeRoomRequest;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import io.swagger.annotations.Api;

/**
 * @author weitichao
 * H5 3D样板间
 *
 */
@Api(value="M站样3D板间API",description="M站3D样板间老接口",tags = "【M-API】")
@Controller
@RequestMapping(value = "/mapi/modelRoom3d")
public class MapiModelRoom3DCoutroller {
	
	private static final Logger LOG = LoggerFactory.getLogger(MapiModelRoom3DCoutroller.class); //日志
	
	@Autowired
	ModelRoomService modelRoomService;
	
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public ResponseEntity<HttpBaseResponse> getModelRoom(@Json HttpModeRoomRequest request){
		if (request != null) {
			LOG.info("MapiModelRoom3DCoutroller getModelRoom request:{}", JsonUtils.obj2json(request));
		}
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		if(null == request){
			baseResponse.setCode(HttpResponseCode.FAILED);
			HttpMessage message = new HttpMessage();
			message.setMsg(MessageConstant.DATA_TRANSFER_EMPTY);
			baseResponse.setExt(message);
		}else {
			ModelRoomDto modelRoomDto = modelRoomService.getModelRoomList(request);
			if(null == modelRoomDto){
				baseResponse.setCode(HttpResponseCode.FAILED);
				HttpMessage message = new HttpMessage();
				message.setMsg(MessageConstant.NO_EXPSTORE_EXIST);
				baseResponse.setExt(message);
			}else{
				baseResponse.setCode(HttpResponseCode.SUCCESS);
				baseResponse.setExt(null);
				baseResponse.setObj(modelRoomDto);
			}
		}
		
		MultiValueMap<String, String> headers = new HttpHeaders();
	    headers.set("Access-Control-Allow-Origin", "*");
	    headers.set("Access-Control-Request-Method", "post");
	    return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
	}
}
