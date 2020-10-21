package com.ihomefnt.o2o.intf.dao.address;

import com.ihomefnt.o2o.intf.domain.address.doo.TReceiveAddressDo;

public interface AddressDao {

	TReceiveAddressDo queryAddressByUserId(Long userId);
	
	void addAddress(TReceiveAddressDo address);
}
