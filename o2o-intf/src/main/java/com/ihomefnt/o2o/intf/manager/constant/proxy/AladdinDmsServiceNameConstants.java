package com.ihomefnt.o2o.intf.manager.constant.proxy;

/**
 * @author huayunlei
 * @ClassName: AladdinDmsServiceNameConstants
 * @Description: aladdin-dms服务名称常量池
 * @date Feb 14, 2019 1:53:55 PM
 */
public interface AladdinDmsServiceNameConstants {

    String API_LABELS = "aladdin-dms.back.deliver.getLabels";
    String API_GETCOMMENT = "aladdin-dms.back.deliver.getComment";
    String API_COMMENT = "aladdin-dms.back.deliver.comment";
    String API_COMMENTCHECK = "aladdin-dms.back.deliver.commentCheck";

    String OWNER_GET_COMMENT = "aladdin-dms.back.deliver.getComment";

    String OWNER_COMMENT = "aladdin-dms.back.deliver.comment";

    String API_GETSCHEDULE = "aladdin-dms.back.deliver.getSchedule";//获取施工进度api

    String DELIVER_GET_NODE_DETAIL_V2 = "aladdin-dms.back.deliver.getNodeDetailV2";

    String API_DELIVER_FINALCHECK = "aladdin-dms.back.deliver.finalCheck";

    String QUERY_DELIVER_ORDERINFO_LIST_BY_ORDER_ID_LIST = "aladdin-dms.back.deliver.queryByOrderIdList";

    String QUERY_ORDER_NODE_TIME = "aladdin-dms.back.deliver.queryOrderNodeTime";

    /**
     * 查询交付中sku状态
     */
    String QUERY_PRODUCT_STATUS_BY_ORDER_ID = "aladdin-dms.deliver.product.queryProductStatusByOrderId";

    /**
     * 一键排期（全品家）
     */
    String SCHEDULE_DATE = "aladdin-dms.app.fast.scheduleDate";
    /**
     * 一键排期（全品家软）
     */
    String SOFT_SCHEDULE_DATE = "aladdin-dms.app.fast.softScheduleDate";
    /**
     * 一键完成开工交底
     */
    String START_SCHEDULE = "aladdin-dms.app.fast.startSchedule";
    /**
     * 一键完成水电
     */
    String FINISH_HYDROPOWER = "aladdin-dms.app.fast.finishHydropower";
    /**
     * 一键完成瓦木
     */
    String FINISH_WOODEN = "aladdin-dms.app.fast.finishWooden";
    /**
     * 一键完成硬装竣工
     */
    String FINISH_HARD = "aladdin-dms.app.fast.finishHard";
    /**
     * 一键采购完成
     */
    String FINISH_PURCHASE = "aladdin-dms.app.fast.finishPurchase";
    /**
     * 一键配送完成
     */
    String FINISH_LOGISTIC = "aladdin-dms.app.fast.finishLogistic";
    /**
     * 一键安装完成
     */
    String FINISH_INSTALL = "aladdin-dms.app.fast.finishInstall";
    /**
     * 一键软装验收
     */
    String FINISH_SOFT_CHECK = "aladdin-dms.app.fast.finishSoftCheck";
    /**
     * 一键艾师傅验收
     */
    String FINISH_CHECK = "aladdin-dms.app.fast.finishCheck";
    /**
     * 一键快速验收
     */
    String FINISH_FAST_CHECK = "aladdin-dms.app.fast.finishFastCheck";
}
