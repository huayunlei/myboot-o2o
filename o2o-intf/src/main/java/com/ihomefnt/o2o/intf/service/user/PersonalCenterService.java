package com.ihomefnt.o2o.intf.service.user;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.right.vo.response.RightsAndOrderResponse;
import com.ihomefnt.o2o.intf.domain.user.vo.*;
import com.ihomefnt.o2o.intf.domain.programorder.dto.AppOrderBaseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.user.vo.response.OrderDeliverAndOrderSaleType;

import java.util.List;

/**
 * 个人中心Service
 *
 * @author liyonggang
 * @create 2019-02-21 09:25
 */
public interface PersonalCenterService {

    /**
     * 根据用户id查询用户个人中心款项记录
     *
     * @param userId
     * @return
     */
    List<PersonalCenterMiddleInfoVo> queryFundRecordForPersonalCenter(Integer userId);

    /**
     * 根据用户id查询用户个人中心我的合同
     *
     * @param userId
     * @return
     */
    List<PersonalCenterMiddleInfoVo> queryUserContractForPersonalCenter(Integer userId);

    /**
     * 查询个人中心我的方案列表信息
     *
     * @param userId
     * @return
     */
    List<PersonalCenterMiddleInfoVo> queryUserProgramListForPersonalCenter(Integer userId);

    /**
     * 根据用户id查询个人中心我的权益
     *
     * @param userId
     * @return
     */
    List<PersonalCenterMiddleInfoVo> queryUserRightsForPersonalCenter(Integer userId, Integer width,String appVersion);

    /**
     * 获取个人中心主体数据
     *
     * @param request
     * @return
     */
    PersonalCenterVo queryPersonalCenter(HttpBaseRequest request);

    /**
     * 获取个人中心角标数据和用户信息
     *
     * @param request
     * @return
     */
    PersonalCenterCornerMarkVo queryPersonalCenterCornerMark(HttpBaseRequest request);

    /**
     * 个人中心查询方案详情
     *
     * @param orderId
     * @param userId
     * @param width
     * @return
     */
    UserProgramForPersonalCenterVo queryUserProgramForPersonalCenter(Integer orderId, Integer userId, Integer osType, Integer width);

    /**
     * 查询用户订单号集合
     *
     * @param userId
     * @return
     */
    List<OrderIdResponse> queryUserOrderIdList(Integer userId);

    /**
     * 根据订单号查权益等级
     *
     * @param orderId
     * @return
     */
    Integer queryRightsByOrderId(Integer orderId);

    /**
     * 查询订单信息
     *
     * @param orderId
     * @return
     */
    AppOrderBaseInfoResponseVo queryOrderInfoByOrderId(Integer orderId);

    /**
     * 查询订单信息
     *
     * @param orderId
     * @return
     */
    AppOrderBaseInfoResponseVo queryAppOrderBaseInfo(Integer orderId);

    /**
     * 查询个人中心我的工地数据
     *
     * @param userId
     * @return
     */
    List<PersonalCenterMiddleInfoVo> queryMyWorkSiteForPersonalCenter(Integer userId);

    /**
     * 查询工地需要的售卖类型和交付信息
     * @param orderId
     * @return
     */
    OrderDeliverAndOrderSaleType queryOrderDeliverAndOrderSaleType(Integer orderId);

     /**
     * 权益等级及金额
     *
     * @param orderId
     * @return
     */
    RightsAndOrderResponse queryRightsInfoByOrderId(Integer orderId);
}
