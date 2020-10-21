/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月9日
 * Description:ArtCommentDao.java 
 */
package com.ihomefnt.o2o.intf.dao.artcomment;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentDto;
import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentPage;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentListRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentViewRequest;

/**
 * @author zhang
 */
public interface ArtCommentDao {

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
	ArtCommentDto viewArtCommentByPK(ArtCommentViewRequest request);

	/**
	 * 根据条件查询评论意见列表
	 * 
	 * @param request
	 * @return
	 */
	ArtCommentPage listArtCommentByCondition(ArtCommentListRequest request);

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
	 * 查询所有艺术品评论列表
	 *
	 * @param opType 0:更新(查询结果包含回复)、1:查询（查询结果不包含回复）
	 * @return
	 */
	List<ArtCommentDto> queryAllCommentList(int opType);
	
	/**
	 * 更新艺术品评论相关信息
	 * @param commentId
	 * @param orderNum
	 * @param productName
	 * @return
	 */
	boolean updateCommentById(ArtCommentDto artCommentDto);

	/**根据订单id、商品id、订单类型查询商品评论列表
	 * @param orderId
	 * @param productId
	 * @param orderType
	 * @return
	 */
	List<ArtCommentDto> getCommentListByOrderIdOrderTypeProdId(Integer orderId, Integer productId, Integer orderType);
}
