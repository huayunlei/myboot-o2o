package com.ihomefnt.o2o.intf.proxy.delivery;


import com.ihomefnt.o2o.intf.domain.delivery.vo.request.ConfirmNodeRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.FinalCheckParamRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.request.GetNodeDetailRequest;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.GetHardScheduleVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.HardDetailVo;
import com.ihomefnt.o2o.intf.domain.delivery.vo.response.NodeDetailVo;
import com.ihomefnt.o2o.intf.domain.homepage.dto.HardScheduleVo;

/**
 * 硬装进度
 */
public interface DeliveryServiceProxy {

    /**
     * 交付单信息接口
     * @param orderId
     * @return
     */
    HardScheduleVo getSchedule(String orderId);


    /**
     * 硬装进度接口
     * @param orderId
     * @return
     */
    HardDetailVo getHardDetail(String orderId);


    /**
     * 节点详细信息
     * @param request
     * @return
     */
    NodeDetailVo getNodeDetail(GetNodeDetailRequest request);

    /**
     * 客户节点验收接口
     * @param request
     * @return
     */
    boolean confirmNode(ConfirmNodeRequest request);


    /**
     * 完整计划日历接口
     * @param orderId
     * @return
     */
    GetHardScheduleVo getHardSchedule(String orderId);


    /**
     * 整体验收接口
     * @param request
     * @return
     */
    boolean finalCheck(FinalCheckParamRequest request);
}
