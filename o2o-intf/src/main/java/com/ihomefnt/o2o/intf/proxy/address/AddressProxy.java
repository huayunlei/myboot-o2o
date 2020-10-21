package com.ihomefnt.o2o.intf.proxy.address;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressAddParamDto;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressResultDto;
import com.ihomefnt.o2o.intf.domain.address.dto.UserAddressUpdateParamDto;

public interface AddressProxy {
	
	/**
	 * 根据用户id查询默认收货地址
	 * 
	 * @param userId
	 * @return
	 */
	UserAddressResultDto queryDefaultByUserId(Integer userId);

	/**
	 * 根据地址id更新地址
	 * 
	 * @param param
	 * @return 1:表示成功,其他表示失败
	 */
	Integer updateById(UserAddressUpdateParamDto param);

	/**
	 * 增加用户收货地址
	 * 
	 * @param param
	 * @return 1:表示成功,其他表示失败
	 */
	Integer addUserAddress(UserAddressAddParamDto param);

	/**
	 * 根据用户id查询到所有收货地址列表
	 * 
	 * @param userId
	 * @return
	 */
	List<UserAddressResultDto> queryUserAddressList(Integer userId);

}
