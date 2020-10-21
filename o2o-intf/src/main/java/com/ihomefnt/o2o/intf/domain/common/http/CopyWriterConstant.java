package com.ihomefnt.o2o.intf.domain.common.http;

/**
 * app文案集合,每个不同模块建不同的内部类，常量名以 模块_ 开头 ，例如ORDER_XXX
 *
 * @author liyonggang
 * @create 2019-02-19 17:14
 */
public interface CopyWriterConstant {

    /**
     * 订单相关
     */
    class Order {
        public static final String ORDER_TOTAL_PRICE = "订单总价";
    }

    /**
     * 用户相关
     */
    class User {

    }
}
