/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月9日
 * Description:ArtCommentDaoImpl.java 
 */
package com.ihomefnt.o2o.service.dao.artcomment;

import com.ihomefnt.o2o.intf.dao.artcomment.ArtCommentDao;
import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentDto;
import com.ihomefnt.o2o.intf.domain.artcomment.dto.ArtCommentPage;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentListRequest;
import com.ihomefnt.o2o.intf.domain.artcomment.vo.request.ArtCommentViewRequest;
import com.ihomefnt.o2o.intf.manager.constant.artcomment.ArtCommentConstant;
import com.ihomefnt.o2o.service.manager.util.mongo.MongoDBUtil;
import com.ihomefnt.o2o.intf.manager.util.common.cache.AppRedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhang
 */
@Service
public class ArtCommentDaoImpl implements ArtCommentDao {

	@Override
	public String createArtComment(ArtCommentDto dto) {
		if (dto == null) {
			return null;
		}
		dto.setCreateTime(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		dto.setDeleteFlag(0);
		MongoDBUtil.insertDocument(dto, ArtCommentConstant.MONGODB_TABLE_PRODUCT_COMMENT);
		String orderKey = ArtCommentConstant.REDIS_ART_ORDER_PREFIX + dto.getOrderId();
		AppRedisUtil.incrBy(orderKey, 1);
		String productPeopleKey = ArtCommentConstant.REDIS_ART_PRODUCT_PEOPLE_PREFIX + dto.getProductId();
		AppRedisUtil.incrBy(productPeopleKey, 1);
		String productScoreKey = ArtCommentConstant.REDIS_ART_PRODUCT_SCORE_PREFIX + dto.getProductId();
		AppRedisUtil.incrBy(productScoreKey, dto.getUserStar() * 20);
		return dto.getCommentId();
	}

	@Override
	public ArtCommentDto viewArtCommentByPK(ArtCommentViewRequest request) {
		if (request == null) {
			return null;
		}
		Integer orderId =request.getOrderId();
		Integer productId =request.getProductId();
		if(orderId == null||productId == null){
			return null;
		}
		Criteria queryBuilder = new Criteria();
		queryBuilder.and("productId").is(productId);
		queryBuilder.and("orderId").is(orderId);
		queryBuilder.and("pid").is("");
		Query query = new Query(queryBuilder);
		List<ArtCommentDto> list=MongoDBUtil.queryDocumentList(ArtCommentConstant.MONGODB_TABLE_PRODUCT_COMMENT, query,
				ArtCommentDto.class);
		if(CollectionUtils.isEmpty(list))
		{
			return null;
		}
		return list.get(0);
	}

	@Override
	public ArtCommentPage listArtCommentByCondition(ArtCommentListRequest request) {
		Integer productId = request.getProductId();
		if (productId == null) {
			return null;
		}
		Integer pageNo = request.getPageNo();
		if (pageNo == null) {
			pageNo = 1;
		}
		Integer pageSize = request.getPageSize();
		if (pageSize == null) {
			pageSize = 1;
		}
		Criteria queryBuilder = new Criteria();
		queryBuilder.and("productId").is(productId);
		queryBuilder.and("deleteFlag").is(0);
		queryBuilder.and("pid").is("");
		Query query = new Query(queryBuilder);
		List<Sort.Order> orders = new ArrayList<>();
		// 按照发布时间降序
		orders.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
		Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(orders));
		List<ArtCommentDto> list = MongoDBUtil.queryDocumentList(ArtCommentConstant.MONGODB_TABLE_PRODUCT_COMMENT,
				query.with(pageable), ArtCommentDto.class);

		ArtCommentPage page = new ArtCommentPage();
		page.setCommentList(list);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		String productPeopleKey = ArtCommentConstant.REDIS_ART_PRODUCT_PEOPLE_PREFIX + productId;
		String productPeopleValue = AppRedisUtil.get(productPeopleKey);
		if (StringUtils.isBlank(productPeopleValue)) {
			productPeopleValue = "0";
		}
		int totalRecords = Integer.parseInt(productPeopleValue);
		page.setTotalRecords(totalRecords);
		String productScoreKey = ArtCommentConstant.REDIS_ART_PRODUCT_SCORE_PREFIX + productId;
		String productScoreValue = AppRedisUtil.get(productScoreKey);
		if (StringUtils.isBlank(productScoreValue)) {
			productScoreValue = "0";
		}
		if (totalRecords == 0) {
			page.setUserScore(0);
		} else {
			page.setUserScore(Integer.parseInt(productScoreValue) / totalRecords);
		}

		int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
		page.setTotalPages(totalPages);
		return page;
	}

	@Override
	public Integer getCommentCountByOrderId(Integer orderId) {
		if (orderId == null) {
			return 0;
		}
		String orderKey = ArtCommentConstant.REDIS_ART_ORDER_PREFIX + orderId;
		String orderValue = AppRedisUtil.get(orderKey);
		if (StringUtils.isBlank(orderValue)) {
			return 0;
		}
		return Integer.parseInt(orderValue);
	}

	@Override
	public List<ArtCommentDto> getCommentListByOrderIdAndProductIdList(Integer orderId, List<Integer> productIdList) {
		Criteria queryBuilder = new Criteria();
		queryBuilder.and("orderId").is(orderId);
		queryBuilder.and("productId").in(productIdList);
		queryBuilder.and("deleteFlag").is(0);
		queryBuilder.and("pid").is("");
		Query query = new Query(queryBuilder);
		List<ArtCommentDto> list = MongoDBUtil.queryDocumentList(ArtCommentConstant.MONGODB_TABLE_PRODUCT_COMMENT,
				query, ArtCommentDto.class);

		return list;
	}

	@Override
	public List<ArtCommentDto> getCommentListByOrderIdList(List<Integer> orderIdList) {
		Criteria queryBuilder = new Criteria();
		queryBuilder.and("orderId").in(orderIdList);
		queryBuilder.and("deleteFlag").is(0);
		queryBuilder.and("pid").is("");
		Query query = new Query(queryBuilder);
		List<ArtCommentDto> list = MongoDBUtil.queryDocumentList(ArtCommentConstant.MONGODB_TABLE_PRODUCT_COMMENT,
				query, ArtCommentDto.class);
		return list;
	}

	@Override
	public List<ArtCommentDto> queryAllCommentList(int opType) {
		Criteria queryBuilder = new Criteria();
		queryBuilder.and("deleteFlag").is(0);
		// 查询并更新艺术品点评数量缓存，不包含回复点评
		if(opType == 1){
			queryBuilder.and("pid").is("");
		}
		Query query = new Query(queryBuilder);
		return MongoDBUtil.queryDocumentList(ArtCommentConstant.MONGODB_TABLE_PRODUCT_COMMENT, query, ArtCommentDto.class);
	}

	@Override
	public boolean updateCommentById(ArtCommentDto artCommentDto) {
		if(org.apache.commons.lang3.StringUtils.isNotBlank(artCommentDto.getCommentId())){
			long result = MongoDBUtil.updateDocumentAsSet(ArtCommentConstant.MONGODB_TABLE_PRODUCT_COMMENT, artCommentDto.getCommentId(), artCommentDto);
			if(result > 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public List<ArtCommentDto> getCommentListByOrderIdOrderTypeProdId(Integer orderId, Integer productId,
			Integer orderType) {
		Criteria queryBuilder = new Criteria();
		queryBuilder.and("orderId").is(orderId);
		queryBuilder.and("productId").is(productId);
		queryBuilder.and("orderType").is(orderType);
		queryBuilder.and("deleteFlag").is(0);
		queryBuilder.and("pid").is("");
		Query query = new Query(queryBuilder);
		List<ArtCommentDto> list = MongoDBUtil.queryDocumentList(ArtCommentConstant.MONGODB_TABLE_PRODUCT_COMMENT,
				query, ArtCommentDto.class);

		return list;
	}

}
