/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月9日
 * Description:ArtCommentService.java 
 */
package com.ihomefnt.o2o.intf.service.artcomment;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentDto;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentListRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentViewRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.response.ArtCommentPageListResponse;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.response.ArtCommentResponse;

/**
 * @author zhang
 */
public interface ArtCommentService {

	/**
	 * 创建艺术品评论
	 * 
	 * @param dto
	 * @return
	 */
	String createArtComment(ArtCommentDto dto);

	/**
	 * 根据评论id查询评论意见
	 * 
	 * @param request
	 * @return
	 */
	ArtCommentResponse viewArtCommentByPK(ArtCommentViewRequest request);

	/**
	 * 根据条件查询评论意见列表
	 * 
	 * @param request
	 * @return
	 */
	ArtCommentPageListResponse listArtCommentByCondition(ArtCommentListRequest request);

	/**
	 * 根据订单Id,查询该订单的评论数量
	 * 
	 * @param orderId
	 * @return
	 */
	Integer getCommentCountByOrderId(Integer orderId);

	/**
	 * 根据订单Id和商品Id,查询该订单的所有的商品评论列表
	 * 
	 * @param orderId
	 * @param productIdList
	 * @return
	 */
	List<ArtCommentDto> getCommentListByOrderIdAndProductIdList(Integer orderId, List<Integer> productIdList);

	/**
	 * 根据订单Id集合，查询这些订单的所有的商品评论列表
	 * 
	 * @param orderIdList
	 * @return
	 */
	List<ArtCommentDto> getCommentListByOrderIdList(List<Integer> orderIdList);

	/**
	 * 整理艺术品评论表数据（添加相关列）
	 * @return
	 */
	String brushArtData();
	
	/**
	 * 将评价数据从mongodb刷新到redis
	 */
	void synCommentDataListFromMongoDBToRedis();

	/**根据订单id、商品id、订单类型查询商品评论列表
	 * @param orderId
	 * @param productId
	 * @param orderType
	 * @return
	 */
	List<ArtCommentDto> getCommentListByOrderIdOrderTypeProdId(Integer orderId, Integer productId, Integer orderType);
}
