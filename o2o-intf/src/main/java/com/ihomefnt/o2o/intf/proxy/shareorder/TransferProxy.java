/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2018年1月23日
 * Description:TransferProxy.java 
 */
package com.ihomefnt.o2o.intf.proxy.shareorder;

import java.util.Map;

import com.ihomefnt.o2o.intf.domain.shareorder.dto.TransferDto;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.PageResponse;

/**
 * @author zhang
 */
public interface TransferProxy {



	/**
	 * 查询满足条件的数据
	 * 
	 * @param params
	 * @return
	 */
	PageResponse<TransferDto> getTransferDtoList(Map<String, Object> params);

}
