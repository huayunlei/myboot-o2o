/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年6月7日
 * Description:IHbmsProxy.java 
 */
package com.ihomefnt.o2o.intf.proxy.lechange;

import com.ihomefnt.o2o.intf.domain.hbms.dto.GetBeginTimeByOrderIdResultDto;
import com.ihomefnt.o2o.intf.domain.hbms.dto.ProjectDetailProgressInfoVo;
import com.ihomefnt.o2o.intf.domain.lechange.dto.*;

import java.util.List;

/**
 * @author zhang
 */
public interface HbmsProxy {
	
	/**
	 * 更新设备的url
	 * @param param
	 * @return
	 */
	boolean updateDeviceUrl(UpdateDeviceParamParamVo param);
	
	
	/**
	 * 查询设备列表
	 * @param param
	 * @return
	 */
	PagesVo<GetDeviceListResultVo> getSimpleDeviceList(GetDeviceListByValueParamVo param);

	PagesVo<GetDeviceListResultVo> getSimpleDeviceList(GetDeviceListParamVo param);

	PagesVo<GetDeviceListResultVo> getDeviceList(GetDeviceListPageVo param);

	/**
	 * 通过设备id查询设备详情
	 * @param cameraSn
	 * @return
	 */
	GetDeviceListResultVo getDeviceBySn(String cameraSn);
	
	/**
	 * 通过订单id查询设备详情(20171019一个订单对应多个摄像头)
	 * @param orderId
	 * @return
	 */
	List<GetDeviceListResultVo> getDeviceByOrderId(String orderId);

    ProjectDetailProgressInfoVo queryHbmsProgress(Integer orderNum);

	GetBeginTimeByOrderIdResultDto getBeginTimeByOrderId(Integer orderNum);


}
