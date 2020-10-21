package com.ihomefnt.o2o.intf.proxy.demo;

import com.ihomefnt.o2o.intf.domain.dms.vo.DemoDeliveryRequestVo;

public interface DemoProxy {

    /**
     *  一键排期（全品家）
     *
     * @param request
     * @return
     */
    String scheduleDate(DemoDeliveryRequestVo request);

    /**
     *一键排期（全品家软）
     *
     * @param request
     * @return
     */
    String softScheduleDate(DemoDeliveryRequestVo request);

    /**
     *一键完成开工交底
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

}
