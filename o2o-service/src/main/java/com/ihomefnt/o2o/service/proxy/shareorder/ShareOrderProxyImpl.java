/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年7月4日
 * Description:ShareOrderProxy.java 
 */
package com.ihomefnt.o2o.service.proxy.shareorder;

import com.ihomefnt.o2o.intf.domain.shareorder.dto.*;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.BuildingtopicViewResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.proxy.WcmWebServiceNameConstants;
import com.ihomefnt.o2o.intf.proxy.shareorder.ShareOrderProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 */
@Service
public class ShareOrderProxyImpl implements ShareOrderProxy {

	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

	/**
	 * 增加日志:主要为了方便定位
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ShareOrderProxyImpl.class);

	@Override
	public String insertShareOrder(ShareOrder params) {
		if (params == null) {
			return null;
		}
		HttpBaseResponse<String> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.INSERT_SHARE_ORDER, params,
					new TypeReference<HttpBaseResponse<String>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		return responseVo.getObj();
	}

	@Override
	public List<ShareOrder> getShareOrderList(HttpShareOrderRequest params) {
		if (params == null) {
			return null;
		}
		HttpBaseResponse<List<ShareOrder>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_SHARE_ORDER_LIST, params,
					new TypeReference<HttpBaseResponse<List<ShareOrder>>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

	@Override
	public List<ShareOrder> getShareOrderListByCommonUser(HttpShareOrderRequest shareOrderRequest, Integer userId) {
		if (shareOrderRequest == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shareOrderRequest", shareOrderRequest);
		params.put("userId", userId);
		HttpBaseResponse<List<ShareOrder>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_SHARE_ORDER_LIST_BY_COMMON_USER, params,
					new TypeReference<HttpBaseResponse<List<ShareOrder>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

	@Override
	public ShareOrder getShareOrderById(HttpShareOrderPraiseRequest params) {
		if (params == null) {
			return null;
		}
		HttpBaseResponse<ShareOrder> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_SHARE_ORDER_BY_ID, params,
					new TypeReference<HttpBaseResponse<ShareOrder>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

	@Override
	public void updateShareOrder(ShareOrder params) {
		if (params == null) {
			return;
		}
		HttpBaseResponse<Integer> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.UPDATE_SHARE_ORDER, params,
					new TypeReference<HttpBaseResponse<Integer>>() {
					});
		} catch (Exception e) {
			return;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return;
		}
	}

	@Override
	public int inc(HttpShareOrderPraiseRequest params) {
		if (params == null) {
			return -1;
		}
		HttpBaseResponse<Integer> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.SHARE_ORDER_INC, params,
					new TypeReference<HttpBaseResponse<Integer>>() {
					});
		} catch (Exception e) {
			return -2;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return -3;
		}
		return responseVo.getObj();
	}

	@Override
	public int getShareOrderCountByUserId(long userId) {
		if (userId <= 0) {
			return -1;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		HttpBaseResponse<Integer> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_SHARE_ORDER_COUNT_BY_USER_ID, params,
					new TypeReference<HttpBaseResponse<Integer>>() {
					});
		} catch (Exception e) {
			return -2;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return -3;
		}
		return responseVo.getObj();
	}

	@Override
	public List<ShareOrderPraise> getShareOrderPraiseListByUserId(long userId) {
		if (userId <= 0) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		HttpBaseResponse<List<ShareOrderPraise>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_SHARE_ORDER_PRAISE_LIST_BY_USER_ID, params,
					new TypeReference<HttpBaseResponse<List<ShareOrderPraise>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

	@Override
	public void insertShareOrderPraise(ShareOrderPraise params) {
		if (params == null) {
			return;
		}
		HttpBaseResponse<Integer> responseVo = null;
		try {
			responseVo = strongSercviceCaller
					.post(WcmWebServiceNameConstants.INSERT_SHARE_ORDER_PRAISE, params,
							new TypeReference<HttpBaseResponse<Integer>>() {
					});
		} catch (Exception e) {
			return;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return;
		}
	}

	@Override
	public int getPraiseCount(Integer userId, String shareOrderId) {
		if (userId == null || shareOrderId == null) {
			return 0;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("shareOrderId", shareOrderId);
		HttpBaseResponse<Integer> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_PRAISE_COUNT, params,
					new TypeReference<HttpBaseResponse<Integer>>() {
					});
		} catch (Exception e) {
			return 0;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return 0;
		}
		return responseVo.getObj();
	}

	@Override
	public String insertShareOrderComment(ShareOrderComment params) {
		if (params == null) {
			return null;
		}
		HttpBaseResponse<String> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.INSERT_SHARE_ORDER_COMMENT, params,
					new TypeReference<HttpBaseResponse<String>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

	@Override
	public List<ShareOrderComment> getShareOrderCommentList(HttpShareOrderCommonListRequest params) {
		if (params == null) {
			return null;
		}
		HttpBaseResponse<List<ShareOrderComment>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_SHARE_ORDER_COMMENT_LIST, params,
					new TypeReference<HttpBaseResponse<List<ShareOrderComment>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

	@Override
	public List<ShareOrderComment> getShareOrderCommentListByCommonUser(
			HttpShareOrderCommonListRequest commentListRequest, Integer userId) {

		if (commentListRequest == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commentListRequest", commentListRequest);
		params.put("userId", userId);
		HttpBaseResponse<List<ShareOrderComment>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_SHARE_ORDER_COMMENT_LIST_BY_COMMON_USER, params,
					new TypeReference<HttpBaseResponse<List<ShareOrderComment>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		return responseVo.getObj();

	}

	@Override
	public boolean deleteShareOrderById(ShareOrder params) {
		if (params == null) {
			return false;
		}

		HttpBaseResponse<Boolean> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.DELETE_SHARE_ORDER_BY_ID, params,
					new TypeReference<HttpBaseResponse<Boolean>>() {
					});
		} catch (Exception e) {
			return false;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return false;
		}
		return responseVo.getObj();

	}

	@Override
	public ShareOrderComment queryShareOrderCommentById(String commentId) {
		if (commentId == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commentId", commentId);

		HttpBaseResponse<ShareOrderComment> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_SHARE_ORDER_COMMENT_BY_ID, params,
					new TypeReference<HttpBaseResponse<ShareOrderComment>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();

	}

	@Override
	public List<ShareOrderComment> queryAllCommentList() {
		Map<String, Object> params = new HashMap<String, Object>();
		HttpBaseResponse<List<ShareOrderComment>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_ALL_COMMENT_LIST, params,
					new TypeReference<HttpBaseResponse<List<ShareOrderComment>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();

	}

	@Override
	public boolean updateCommentById(ShareOrderComment params) {
		if (params == null) {
			return false;
		}

		HttpBaseResponse<Boolean> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.UPDATE_COMMENT_BY_ID, params,
					new TypeReference<HttpBaseResponse<Boolean>>() {
					});
		} catch (Exception e) {
			return false;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return false;
		}
		return responseVo.getObj();

	}

	@Override
	public int queryOfficialCountByShareOrderId(String shareOrderId) {
		if (shareOrderId == null) {
			return 0;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shareOrderId", shareOrderId);

		HttpBaseResponse<Integer> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_OFFICIAL_COUNT_BY_SHARE_ORDER_ID, params,
					new TypeReference<HttpBaseResponse<Integer>>() {
					});
		} catch (Exception e) {
			return 0;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return 0;
		}
		return responseVo.getObj();

	}

	@Override
	public List<ShareOrder> queryAllShareOrderList() {
		Map<String, Object> params = new HashMap<String, Object>();

		HttpBaseResponse<List<ShareOrder>> responseVo = null;
		try {
			responseVo = strongSercviceCaller
					.post(WcmWebServiceNameConstants.QUERY_ALL_SHARE_ORDER_LIST, params,
							new TypeReference<HttpBaseResponse<List<ShareOrder>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();

	}

	@Override
	public boolean updateShareOrderById(ShareOrder params) {
		if (params == null) {
			return false;
		}

		HttpBaseResponse<Boolean> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.UPDATE_SHARE_ORDER_BY_ID, params,
					new TypeReference<HttpBaseResponse<Boolean>>() {
					});
		} catch (Exception e) {
			return false;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return false;
		}
		return responseVo.getObj();

	}

	@Override
	public int addPraiseByType(Integer userId, String shareOrderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("id", shareOrderId);
		HttpBaseResponse<Integer> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.PRAISE_BUILDING_TOPIC_FOR_APP, params,
					new TypeReference<HttpBaseResponse<Integer>>() {
					});
		} catch (Exception e) {
			return 0;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return 0;
		}

		return responseVo.getObj();
	}

	@Override
	public BuildingtopicViewResponse queryBuildingtopicViewResponse(String shareOrderId, Integer userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", shareOrderId);
		params.put("userId", userId);
		HttpBaseResponse<BuildingtopicViewResponse> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.VIEW_BUILDING_TOPIC_FOR_APP, params,
					new TypeReference<HttpBaseResponse<BuildingtopicViewResponse>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();

	}

	@Override
	public int forward(HttpForwardRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", request.getShareOrderId());
		params.put("token", request.getAccessToken());
		HttpBaseResponse<Integer> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.FORWARD_FOR_APP, params,
					new TypeReference<HttpBaseResponse<Integer>>() {
					});
		} catch (Exception e) {
			return 0;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return 0;
		}
		return responseVo.getObj();
	}

	@Override
	public String addShareOrderCommentForTopic(HttpShareOrderCommentRequest request, UserDto user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("topicId", request.getShareOrderId());
		params.put("replyCommentId", request.getCommentId());
		params.put("comment", request.getComment());
		params.put("createUserId", user.getId());
		params.put("createMobile", user.getMobile());
		if (user.getMember() != null && StringUtils.isNotEmpty(user.getMember().getNickName())) {
			params.put("createNickname", user.getMember().getNickName());
		}
		HttpBaseResponse<String> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.CREATE_COMMENT_FOR_APP, params,
					new TypeReference<HttpBaseResponse<String>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

	@Override
	public String addShareOrderForArticle(ShareOrderDto request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("topicId", request.getTopicId());
		params.put("articleContent", request.getTextContent());
		params.put("articleImgs", request.getImgContent());
		params.put("token", request.getAccessToken());
		HttpBaseResponse<String> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.CREATE_ARTICLE_FOR_APP, params,
					new TypeReference<HttpBaseResponse<String>>() {
					});
		} catch (Exception e) {
			return null;
		}
		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

	@Override
	public List<ShareOrderComment> getShareOrderCommentListForTopic(HttpShareOrderCommonListRequest commentListRequest,
			Integer userId, boolean superuserTag) {
		if (commentListRequest == null) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		int page = commentListRequest.getPage();
		if (page == 0) {

			params.put("pageNo", 1);
		} else {
			params.put("pageNo", page);
		}
		int limit = commentListRequest.getLimit();
		if (limit == 0) {
			params.put("pageSize", 30);
		} else {
			params.put("pageSize", limit);
		}
		params.put("topicId", commentListRequest.getShareOrderId());
		params.put("userId", userId);
		params.put("superuserTag", superuserTag);

		HttpBaseResponse<List<ShareOrderComment>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.QUERY_COMMENT_LIST_FOR_APP, params,
					new TypeReference<HttpBaseResponse<List<ShareOrderComment>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}

		return responseVo.getObj();
	}

	@Override
	public List<ShareOrder> getShareOrderListByIds(List<String> shareIdList) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shareIdList", shareIdList);
		HttpBaseResponse<List<ShareOrder>> responseVo = null;
		try {
			responseVo = strongSercviceCaller.post(WcmWebServiceNameConstants.GET_SHARE_ORDER_LIST_BY_IDS, params,
					new TypeReference<HttpBaseResponse<List<ShareOrder>>>() {
					});
		} catch (Exception e) {
			return null;
		}

		if (responseVo == null || responseVo.getObj() == null) {
			return null;
		}
		return responseVo.getObj();
	}

}
