/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年5月25日
 * Description:CashProxy.java
 */
package com.ihomefnt.o2o.intf.proxy.order;

import com.ihomefnt.o2o.intf.domain.order.dto.CashierRecordResultVo;

import java.util.List;

/**
 * @author zhang
 */
public interface CashProxy {

	/**
	 * 根据订单id查询收款记录
	 * 
	 * @param orderId
	 * @return
	 */
	List<CashierRecordResultVo> queryCashierRecordsByOrderId(Integer orderId);

}
