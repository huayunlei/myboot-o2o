package com.ihomefnt.o2o.intf.manager.constant.dms;

public class DmsProductStatusConstants {

    private DmsProductStatusConstants() {
        throw new IllegalStateException("Utility class DmsProductStatusConstants");
    }

    // 待采购
    public static final Integer WAITING_PURCHASE = 1000;

    // 硬装服务-未完成
    public static final Integer UN_COMPLETED = 1010;

    // 待厂家接单
    public static final Integer WAITING_MANUFACTURER_RECEIVE = 3010;

    // 待厂家出货
    public static final Integer WAITING_MANUFACTURER_SHIPMENT = 3020;

    // 厂家出货完成
    public static final Integer MANUFACTURER_SHIPMENT_COMPLETED = 3040;

    // 待创建物流订单
    public static final Integer WAITING_LOGISTICS_ORDER = 5000;

    // 待派物流单
    public static final Integer WAITING_SEND_LOGISTICS = 5010;

    // 待接单
    public static final Integer WAITING_ORDERS = 5020;

    // 待揽收
    public static final Integer WAITING_COLLECT = 5030;

    // 待发货
    public static final Integer WAITING_SHIPMENT = 5040;

    // 待到货
    public static final Integer WAITING_ARRIVAL = 5050;

    // 待安装
    public static final Integer WAITING_INSTALL = 5060;

    // 待验收
    public static final Integer WAITING_CHECK = 5070;

    // 完结
    public static final Integer COMPLETED = 8888;

    // 取消
    public static final Integer CANCEL = 9999;
}
