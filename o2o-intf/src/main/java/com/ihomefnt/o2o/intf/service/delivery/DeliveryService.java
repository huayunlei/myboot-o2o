package com.ihomefnt.o2o.intf.service.delivery;


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
public interface DeliveryService {

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
    HardDetailVo getHardDetail(String orderId,String appVersion);

    /**
     * 节点详细信息
     * @param request
     * @return
     */
    @Deprecated
    NodeDetailVo getNodeDetail(GetNodeDetailRequest request);

    /**
     * 客户节点验收接口
     * @param request
     */
    void confirmNode(ConfirmNodeRequest request);

    /**
     * 完整计划日历接口
     * @param orderId
     * @return
     */
    GetHardScheduleVo getHardSchedule(String orderId);


    /**
     * 整体验收接口
     * @param request
     */
    void finalCheck(FinalCheckParamRequest request);

    /**
     * 新版交付详情V2
     * @param request
     * @return
     */
    NodeDetailVo getNodeDetailV2(GetNodeDetailRequest request);
}
