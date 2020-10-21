package com.ihomefnt.o2o.intf.manager.constant.demo;

public class DemoStatusNameConstants {

    private DemoStatusNameConstants() {
        throw new IllegalStateException("Utility class DemoStatusNameConstants");
    }

    // 取消订单
    public static final String CANCEL_ORDER = "cancelOrder";

    // 待付定金
    public static final String NEED_PAY_EARNEST = "needPayEarnest";

    // 待完成设计任务
    public static final String NEED_COMPLETE_DESIGN_DEMAND = "needCompleteDesignDemand";

    // 待添加设计任务
    public static final String NEED_ADD_DESIGN_DEMAND = "needAddDesignDemand";

    // 待付合同款
    public static final String NEED_PAY_CONTRACT = "needPayContract";

    // 待确认开工
    public static final String NEED_CONFIRM_START = "needConfirmStart";

    // 修改交房日期
    public static final String WAIT_DELIVER_HOUSE = "waitDeliverHouse";

    // 安排施工计划
    public static final String NEED_PLAN = "needPlan";

    // 安排施工计划（软）
    public static final String NEED_PLAN_SOFT = "needPlanSoft";

    // 开工交底
    public static final String WAIT_CONSTRICTION = "waitConstriction";

    // 艾师傅验收
    public static final String MANAGER_CHECK = "managerCheck";

    // 硬装水电阶段
    public static final String HARD_HYDROPOWER = "hardHydropower";

    // 硬装瓦木阶段
    public static final String HARD_WOODEN = "hardWooden";

    // 硬装竣工阶段
    public static final String HARD_COMPLETE = "hardComplete";

    // 软装已全部到货
    public static final String SOFT_RECIEVED = "softRecieved";

    // 未全部采购
    public static final String SOFT_NOT_BUY = "softNotBuy";

    // 软装未全部到货
    public static final String SOFT_NOT_RECIEVED = "softNotRecieved";

    // 急速验收
    public static final String FAST_CHECK = "fastCheck";

    // 待验收
    public static final String NEED_ACCEPT = "needAccept";

    // 待评价
    public static final String NEED_COMMENT = "needComment";
}
