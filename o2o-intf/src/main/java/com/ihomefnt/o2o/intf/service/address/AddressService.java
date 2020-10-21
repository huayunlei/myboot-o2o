package com.ihomefnt.o2o.intf.service.address;

import com.ihomefnt.o2o.intf.domain.address.vo.request.QueryReceiveAddressRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.request.ReceiveAddressRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.request.SelReceiveAddressRequestVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.ReceiveAddressResponseVo;
import com.ihomefnt.o2o.intf.domain.address.vo.response.TReceiveAddressResponseVo;

public interface AddressService {

	/** 
	* @Title: queryAddress 
	* @Description: 查询用户地址 
	* @param @param request
	* @return ReceiveAddressResponse    返回类型 
	* @throws 
	*/
	ReceiveAddressResponseVo queryAddress(QueryReceiveAddressRequestVo request);

	/** 
	* @Title: saveAddress 
	* @Description: 添加用户地址
	* @param @param request  参数说明 
	* @return int    返回类型 
	* @throws 
	*/
	int saveAddress(ReceiveAddressRequestVo request);

	TReceiveAddressResponseVo selAddress(SelReceiveAddressRequestVo request);

}
