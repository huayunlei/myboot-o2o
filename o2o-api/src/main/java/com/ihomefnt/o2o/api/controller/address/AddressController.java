package com.ihomefnt.o2o.api.controller.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ihomefnt.o2o.intf.domain.address.vo.request.QueryReceiveAddressRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.request.ReceiveAddressRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.request.SelReceiveAddressRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.ReceiveAddressResponseVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.TReceiveAddressResponseVo;
import com.ihomefnt.o2o.intf.service.address.AddressService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.bean.Json;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/address")
@Api(tags = "【地址API】")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	/**
     * 功能描述：查询用户地址
     */
	@ApiOperation(value="queryAddress",notes="查询用户地址")
	@RequestMapping(value = "/queryAddress",method = RequestMethod.POST)
    public HttpBaseResponse<ReceiveAddressResponseVo> queryAddress(@Json QueryReceiveAddressRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}

		ReceiveAddressResponseVo obj = addressService.queryAddress(request);
		return HttpBaseResponse.success(obj);
	}
	
	/**
     * 功能描述：添加用户地址
     */
	@ApiOperation(value="saveAddress",notes="添加用户地址")
	@RequestMapping(value = "/saveAddress",method = RequestMethod.POST)
    public HttpBaseResponse<Void> saveAddress(@Json ReceiveAddressRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}

		int result = addressService.saveAddress(request);
		if (result == 1) {
			return HttpBaseResponse.success();
		}
		return HttpBaseResponse.fail(MessageConstant.FAILED);
	}
	
	/**
     * 功能描述：selAddress
     */
	@ApiOperation(value="selAddress",notes="selAddress")
	@RequestMapping(value = "/selAddress",method = RequestMethod.POST)
    public HttpBaseResponse<TReceiveAddressResponseVo> selAddress(@Json SelReceiveAddressRequestVo request) {
		if (request == null) {
			return HttpBaseResponse.fail(MessageConstant.DATA_TRANSFER_EMPTY);
		}

		TReceiveAddressResponseVo obj = addressService.selAddress(request);
		return HttpBaseResponse.success(obj);
	}

}
