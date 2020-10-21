package com.ihomefnt.o2o.intf.manager.constant.order;

public interface OrderConstants {

    /**
     * 全品家订单ID上限
     */
    Integer CRITICAL_VALUE_UPPER = 99999999;
    /**
     * 全品家订单ID下限
     */
    Integer CRITICAL_VALUE_LOWER = 10000000;

    int ORDER_SYSTEM_SOURCE_OMS = 1;

    int ORDER_SYSTEM_SOURCE_ALADDIN = 2;

    // 不可看
    Integer NO_VIEW = 1;

    // 仅可看
    Integer ONLY_VIEW = 2;

    // 可下单
    Integer AVALIABLE_ORDER = 3;


    public static final int ORDER_SOFT = 1;//软装订单
    public static final int ORDER_HARD = 2;//硬装订单
    public static final int ORDER_FAMILY = 3;//全品家订单
    public static final int ORDER_DEAL = 4;//抢购订单
    public static final int ORDER_ART = 5;//艺术品订单
    public static final int ORDER_TRIP = 6;//文旅商品订单
    public static final int ORDER_REJECTED = 7;// 商品退单订单
    public static final int ORDER_LOFT = 8;// 样板间申请订单
    public static final int ORDER_SOLUTION = 9;// 方案订单
    public static final int ORDER_SOLUTION_FREEDOM = 10;// 自由搭配方案订单

}
