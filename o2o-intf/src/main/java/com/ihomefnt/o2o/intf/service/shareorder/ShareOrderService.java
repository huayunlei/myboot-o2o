package com.ihomefnt.o2o.intf.service.shareorder;

import com.ihomefnt.o2o.intf.domain.shareorder.dto.*;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.ShareOrderDetailResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.UserHouseInfoResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBasePageResponse;

import java.util.List;

/**
 * Created by onefish on 2016/11/3 0003.
 */
public interface ShareOrderService {
    /**
     * 获取新家大晒列表
     * @param shareOrderRequest
     * @return
     */
    @SuppressWarnings("rawtypes")
	HttpBasePageResponse getShareOrderList(HttpShareOrderRequest shareOrderRequest);

	/**
	 * 发表新家大晒
	 * 
	 * @param shareOrderDto
	 * @return
	 */
	String addShareOrder(ShareOrderDto shareOrderDto);

	/**
	 * 发表专题:投稿
	 * 
	 * @param shareOrderDto
	 * @return
	 */
	String addShareOrderForArticle(ShareOrderDto shareOrderDto);

    /**
     * 新家大晒点赞
     * @param shareOrderPraiseRequest
     * @return 点赞后的点赞数
     */
	Integer addPraise(HttpShareOrderPraiseRequest shareOrderPraiseRequest);
    

	/**
	 * 新家大晒点赞
	 * @param shareOrderPraiseRequest
	 * @param type:1 表示专题
	 * @return
	 */
	Integer addPraiseByType(HttpShareOrderPraiseRequest shareOrderPraiseRequest, int type);

    /**
     * 新家大晒用户评论
     * @param shareOrderCommentRequest
     * @return
     */
    String addShareOrderComment(HttpShareOrderCommentRequest shareOrderCommentRequest);
    
    /**
     * 类型为专题的用户评论
     * @param shareOrderCommentRequest
     * @return
     */
	String addShareOrderCommentForTopic(HttpShareOrderCommentRequest shareOrderCommentRequest,UserDto user);
    
    /**
     * 获取新晒大家评论接口
     * @return
     */
    List<ShareOrderComment> getShareOrderCommentList(HttpShareOrderCommonListRequest shareOrderCommonListRequest);
    
    /**
     * 临时使用导出,只使用一次:统计截止到1月24日的晒家内容
     * @param shareOrderRequest
     */
    void exportList(HttpShareOrderRequest shareOrderRequest);
    
    /**
     * 根据晒家ID删除晒家
     * @param shareOrderId
     * @return
     */
    boolean deleteShareOrderById(String shareOrderId);
    
    /**
     * 根据手机号查询用户房屋信息
     * @param userId
     * @return
     */
    UserHouseInfoResponse queryUserHouseInfo(Integer userId,Integer orderId);

	/**
	 * 整理晒家表数据（添加相关列）
	 * @return
	 */
	String brushShareOrderData();

	/**
	 * 整理晒家评论表数据（添加相关列）
	 * @return
	 */
	String brushShareOrderCommentData();
	
	/**
	 * 查询晒家详情
	 * @param request
	 * @return
	 */
	ShareOrderDetailResponse queryShareOrderDetailById(ShareOrderDetailRequest request);

	/**
	 * 查询新的楼盘运营
	 * @param shareOrderId
	 * @return
	 */
	ShareOrderDetailResponse queryShareOrderDetailByType(String shareOrderId,Integer userId);

	/**
	 * 转发晒家
	 * @param request
	 * @return
	 */
	Integer forward(HttpForwardRequest request);

}
