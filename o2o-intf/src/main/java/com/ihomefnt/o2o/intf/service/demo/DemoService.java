package com.ihomefnt.o2o.intf.service.demo;

import com.ihomefnt.o2o.intf.domain.demo.vo.request.CompleteDesignDemandRequest;
import com.ihomefnt.o2o.intf.domain.demo.vo.request.DemoCommonRequest;
import com.ihomefnt.o2o.intf.domain.demo.vo.response.DemoCommonResponse;
import com.ihomefnt.o2o.intf.domain.dms.vo.DemoDeliveryRequestVo;

public interface DemoService {

    /**
     * 判断是否可进入演示模式
     *
     * @param mobileNum
     * @return
     */
    Boolean judgeCanDemo(String mobileNum);

    /**
     * 判断是否白名单
     *
     * @param orderId
     * @return
     */
    Boolean judgeBuildingIsWhite(Integer orderId);

    /**
     * 查询按钮列表
     *
     * @param request
     * @return
     */
    DemoCommonResponse queryDemoButtonInfo(DemoCommonRequest request);

    /**
     * 一键付定金
     *
     * @param request
     * @return
     */
    String payEarnest(DemoCommonRequest request);

    /**
     * 一键完成设计任务
     *
     * @param request
     * @return
     */
    String completeDesignDemand(DemoCommonRequest request);

    /**
     * 一键完成设计任务
     *
     * @param request
     * @return
     */
    String completeDesignDemandBySolutionId(CompleteDesignDemandRequest request);

    /**
     * 一键付合同款
     *
     * @param request
     * @return
     */
    String payContract(DemoCommonRequest request);

    /**
     * 更新交房日期
     *
     * @param request
     * @return
     */
    String updateDeliverTime(DemoCommonRequest request);



    /**
     *  一键排期（全品家）
     *
     * @param request
     * @return
     */
    String scheduleDate(DemoDeliveryRequestVo request);

    /**
     * 一键排期（全品家软）
     *
     * @param request
     * @return
     */
    String softScheduleDate(DemoDeliveryRequestVo request);

    /**
     * 一键完成开工交底
     *
     * @param request
     * @return
     */
    String startSchedule(DemoDeliveryRequestVo request);

    /**
     * 一键完成水电
     *
     * @param request
     * @return
     */
    String finishHydropower(DemoDeliveryRequestVo request);

    /**
     * 一键完成瓦木
     *
     * @param request
     * @return
     */
    String finishWooden(DemoDeliveryRequestVo request);

    /**
     * 一键完成硬装竣工
     *
     * @param request
     * @return
     */
    String finishHard(DemoDeliveryRequestVo request);

    /**
     * 一键采购完成
     *
     * @param request
     * @return
     */
    String finishPurchase(DemoDeliveryRequestVo request);

    /**
     * 一键配送完成
     *
     * @param request
     * @return
     */
    String finishLogistic(DemoDeliveryRequestVo request);

    /**
     * 一键安装完成
     *
     * @param request
     * @return
     */
    String finishInstall(DemoDeliveryRequestVo request);

    /**
     * 一键软装验收
     *
     * @param request
     * @return
     */
    String finishSoftCheck(DemoDeliveryRequestVo request);

    /**
     * 一键艾师傅验收
     *
     * @param request
     * @return
     */
    String finishCheck(DemoDeliveryRequestVo request);

    /**
     * 一键快速验收
     *
     * @param request
     * @return
     */
    String finishFastCheck(DemoDeliveryRequestVo request);

    /**
     * 取消订单
     *
     * @param request
     * @return
     */
    String cancelOrder(DemoDeliveryRequestVo request);
}
