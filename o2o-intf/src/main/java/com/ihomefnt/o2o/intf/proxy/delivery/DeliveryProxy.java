package com.ihomefnt.o2o.intf.proxy.delivery;


import com.ihomefnt.o2o.intf.domain.delivery.dto.DeliverOrderInfo;
import com.ihomefnt.o2o.intf.domain.delivery.dto.ProductStatusListVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.ConfirmNodeRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.FinalCheckParamRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.GetNodeDetailRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.*;
import com.ihomefnt.o2o.intf.domain.homepage.dto.HardScheduleVo;

import java.util.List;

/**
 * 硬装进度
 */
public interface DeliveryProxy {

    /**
     * 交付单信息接口
     *
     * @param orderId
     * @return
     */
    HardScheduleVo getSchedule(String orderId);


    /**
     * 硬装进度接口
     *
     * @param orderId
     * @return
     */
    HardDetailVo getHardDetail(String appVersion, String orderId);


    /**
     * 节点详细信息
     *
     * @param request
     * @return
     */
    @Deprecated
    NodeDetailVo getNodeDetail(GetNodeDetailRequest request);

    /**
     * 客户节点验收接口
     *
     * @param request
     * @return
     */
    boolean confirmNode(ConfirmNodeRequest request);


    /**
     * 完整计划日历接口
     *
     * @param orderId
     * @return
     */
    GetHardScheduleVo getHardSchedule(String orderId);


    /**
     * 整体验收接口
     *
     * @param request
     * @return
     */
    boolean finalCheck(FinalCheckParamRequest request);

    List<DeliverInfoVo> queryByOrderIdList(List<Integer> orderIdList);

    DmsRequiredVo queryOrderDeliverInfo(Integer orderNum);

    List<ProductStatusListVo> queryProductStatus(Integer orderNum);

    /**
     * hbms施工节点详情V2
     *
     * @param nodeId
     * @param orderId
     * @return
     */
    NodeDetailVo getNodeDetailV2(String nodeId, Integer orderId);


    /**
     * 根据订单号批量查询订单交付信息
     *
     * @param orderIdList
     * @return
     */
    List<DeliverOrderInfo> queryDeliverOrderInfoListByOrderIdList(List<Integer> orderIdList);
}
