/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2016年12月30日
 * Description:IArtOrderProxy.java 
 */
package com.ihomefnt.o2o.intf.proxy.art;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.OrderDto;
import com.ihomefnt.o2o.intf.domain.collage.vo.request.QueryCollageOrderDetailRequest;

/**
 * 订单代理类
 * @author zhang
 */
public interface ArtOrderProxy {
	
	/**
	 * 创建艺术品订单
	 *  code:1 创建成功, 0创建失败, -1库存不足,  -2 艾积分不足
	 * @param param
	 * @return
	 */
	ResponseVo<?> createArtOrder(Object param); 	
	
	/**
	 * 艺术品订单取消
	 * @param param
	 * @return
	 */
	boolean artCancel(Object param); 
	
	/**
	 * 根据订单ID查询艺术品订单详情
	 * @param orderId
	 * @return
	 */
	OrderDto queryArtOrderDetailById(Integer orderId);
	
	/**
	 * 删除订单
	 * @param request
	 */
	void deleteOrder(QueryCollageOrderDetailRequest request);
}
