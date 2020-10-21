package com.ihomefnt.o2o.intf.manager.util.order;

import com.ihomefnt.o2o.intf.manager.constant.order.OrderConstant;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;

/**
 * 全品家订单状态装换工具
 * @author xiamingyu
 * @date 2018/9/10
 */


public class FamilyOrderStatus {
    private FamilyOrderStatus(){}

    /**
     * 后台订单状态转前端状态
     * @param orginOrderStatus
     * @return
     */
    public static Integer getOrderStatus(Integer orginOrderStatus) {
        Integer orderStatus = -1;
        // 订单状态扭转
        if (orginOrderStatus != null) {
            if (OrderConstant.ORDER_OMSSTATUS_TOUCH.equals(orginOrderStatus)) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_TOUCH;// 接触状态
            } else if (OrderConstant.ORDER_OMSSTATUS_PURPOSE.equals(orginOrderStatus)) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_PURPOSE;// 意向状态
            } else if (OrderConstant.ORDER_OMSSTATUS_HANDSEL.equals(orginOrderStatus)) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_HANDSEL;// 定金状态
            } else if (OrderConstant.ORDER_OMSSTATUS_SIGN.equals(orginOrderStatus)) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_SIGN;// 签约状态
            } else if (OrderConstant.ORDER_OMSSTATUS_DELIVERY.equals(orginOrderStatus)) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_DELIVERY;// 交付中状态
            } else if (OrderConstant.ORDER_OMSSTATUS_FINISH.equals(orginOrderStatus)) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_FINISH;// 已完成状态
            } else if (OrderConstant.ORDER_OMSSTATUS_CANCEL.equals(orginOrderStatus)) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_CANCEL;// 已取消状态
            } else if (OrderConstant.ORDER_OMSSTATUS_PRE_DELIVERY.equals(orginOrderStatus)) {
                orderStatus = ProductProgramPraise.ALADDIN_ORDER_STATUS_SIGN;//  签约状态
            }
        }

        return orderStatus;
    }
}
