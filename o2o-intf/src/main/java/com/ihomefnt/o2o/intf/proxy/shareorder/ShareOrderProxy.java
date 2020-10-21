package com.ihomefnt.o2o.intf.proxy.shareorder;

import com.ihomefnt.o2o.intf.domain.shareorder.dto.*;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.BuildingtopicViewResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;

import java.util.List;

/**
 * Created by onefish on 2016/11/3 0003.
 */
public interface ShareOrderProxy {

	/**
	 * 发表晒家
	 * 
	 * @param shareOrder
	 * @return
	 */
	String insertShareOrder(ShareOrder shareOrder);

	/**
	 * 发表专题之投稿
	 * 
	 * @param shareOrder
	 * @return
	 */
	String addShareOrderForArticle(ShareOrderDto shareOrderDto);

	List<ShareOrder> getShareOrderList(HttpShareOrderRequest shareOrderRequest);

	/**
	 * 查询满足条件的普通用户的晒家列表:普通用户只能看 审批通过和 自己发布的未审批通过
	 * 
	 * @return
	 */
	List<ShareOrder> getShareOrderListByCommonUser(HttpShareOrderRequest shareOrderRequest, Integer userId);

	ShareOrder getShareOrderById(HttpShareOrderPraiseRequest shareOrderPraiseRequest);

	void updateShareOrder(ShareOrder shareOrder);

	int inc(HttpShareOrderPraiseRequest shareOrderPraiseRequest);

	int getShareOrderCountByUserId(long userId);

	List<ShareOrderPraise> getShareOrderPraiseListByUserId(long userId);

	void insertShareOrderPraise(ShareOrderPraise shareOrderPraiseRequest);

	int getPraiseCount(Integer userId, String shareOrderId);

	/**
	 * 发表用户评论:类型 type 0 老晒家
	 * 
	 * @param comment
	 * @return
	 */
	String insertShareOrderComment(ShareOrderComment comment);

	/**
	 * 发表用户评论:类型 type 1 专题
	 * 
	 * @param shareOrderCommentRequest
	 * @return
	 */
	String addShareOrderCommentForTopic(HttpShareOrderCommentRequest shareOrderCommentRequest, UserDto user);

	List<ShareOrderComment> getShareOrderCommentList(HttpShareOrderCommonListRequest commentListRequest);

	/**
	 * 根据晒家id集合获取普通用户的晒家评论集合
	 * 
	 * @param commentListRequest
	 * @return
	 */
	List<ShareOrderComment> getShareOrderCommentListByCommonUser(HttpShareOrderCommonListRequest commentListRequest,
			Integer userId);

	/**
	 * 根据晒家id,普通用户,特需用户来查询晒家评论集合
	 * 
	 * @param commentListRequest
	 *            晒家id
	 * @param userId
	 *            普通用户id
	 * @param superuserTag
	 *            true:是管理员,否:不是管理员
	 * @return
	 */
	List<ShareOrderComment> getShareOrderCommentListForTopic(HttpShareOrderCommonListRequest commentListRequest,
			Integer userId, boolean superuserTag);

	/**
	 * 根据晒家ID删除晒家
	 * 
	 * @param shareOrderId
	 * @return
	 */
	boolean deleteShareOrderById(ShareOrder shareOrder);

	/**
	 * 根据评论ID查询评论详情
	 * 
	 * @param commentId
	 * @return
	 */
	ShareOrderComment queryShareOrderCommentById(String commentId);

	/**
	 * 查询所有晒家评论
	 * 
	 * @return
	 */
	List<ShareOrderComment> queryAllCommentList();

	/**
	 * 更新晒家评论相关信息
	 * 
	 * @param commentId
	 * @return
	 */
	boolean updateCommentById(ShareOrderComment shareOrderComment);

	/**
	 * 根据晒家ID查询官方回复数量
	 * 
	 * @param shareOrderId
	 * @return
	 */
	int queryOfficialCountByShareOrderId(String shareOrderId);

	/**
	 * 查询所有晒家列表
	 * 
	 * @return
	 */
	List<ShareOrder> queryAllShareOrderList();

	/**
	 * 根据晒家ID更新相关信息
	 * 
	 * @return
	 */
	boolean updateShareOrderById(ShareOrder shareOrder);

	/**
	 * 用户点赞
	 * 
	 * @param userId
	 * @param shareOrderId
	 * @return
	 */
	int addPraiseByType(Integer userId, String shareOrderId);

	/**
	 * 查询楼盘运营的详情
	 * 
	 * @param shareOrderId
	 * @return
	 */
	BuildingtopicViewResponse queryBuildingtopicViewResponse(String shareOrderId, Integer userId);

	/**
	 * 用户转发
	 * 
	 * @param request
	 * @return
	 */
	int forward(HttpForwardRequest request);

	/**
	 * 查询指定id集合
	 * 
	 * @param shareIdList
	 * @return
	 */
	List<ShareOrder> getShareOrderListByIds(List<String> shareIdList);

}
