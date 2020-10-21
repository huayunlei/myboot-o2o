package com.ihomefnt.o2o.intf.proxy.user;

import com.ihomefnt.o2o.intf.domain.user.dto.*;
import com.ihomefnt.o2o.intf.domain.user.vo.ResourceVo;

import java.util.List;
import java.util.Map;

/**
 * 个人中心Proxy
 *
 * @author liyonggang
 * @create 2019-02-21 09:26
 */
public interface PersonalCenterProxy {
    /**
     * 从wcm查询个人中心资源
     *
     * @return
     */
    List<ResourceVo> queryPersonalCenterResoureData();

    /**
     * 根据用户id查询订单列表
     *
     * @param userId
     * @return
     */
    List<AppMasterOrderResultDto> queryMasterOrderListByUserId(Integer userId);

    /**
     * 根据大订单号批量查询大订单详情
     *
     * @param orderIds
     */
    List<HbmsOrderDetailDto> batchQueryMasterOrderDetail(List<Integer> orderIds);


    /**
     * 根据大订单号批量查询大订单详情
     *
     * @param orderId
     */
    AppMasterOrderDetailDto queryMasterOrderDetail(Integer orderId);


    /**
     * 根据订单号批量查询草稿信息
     *
     * @param orderIds
     * @return
     */
    Map<Integer, OrderDraftDto> queryOrderDraftDtoByOrderIds(List<Integer> orderIds);

    /**
     * 查询可升级权益
     * @param collect
     * @return
     */
    List<UpgradeInfoDto> batchUpgradeInfo(List<Integer> collect);
}
